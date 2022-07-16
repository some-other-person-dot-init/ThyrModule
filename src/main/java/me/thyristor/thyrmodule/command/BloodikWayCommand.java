package me.thyristor.thyrmodule.command;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.ChunkCoordIntPair;
import net.minecraft.server.v1_16_R3.IBlockData;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftChunk;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class BloodikWayCommand implements CommandExecutor {
    int[] worldLimits = {96, 96, 30624, 15792};
    private static Material[] ORES = {
            Material.COAL_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.GOLD_ORE,
            Material.IRON_ORE,
            Material.REDSTONE_ORE
    };
    private boolean isOre(Material mat) {
        for (Material ore: ORES) {
            if (mat == ore)
                return true;
        }
        return false;
    }
    private static Material setMaterial = Material.STONE;

    private static IBlockData[] NMS_ORES = {
            Blocks.COAL_ORE.getBlockData(),
            Blocks.DIAMOND_ORE.getBlockData(),
            Blocks.EMERALD_ORE.getBlockData(),
            Blocks.GOLD_ORE.getBlockData(),
            Blocks.IRON_ORE.getBlockData(),
            Blocks.REDSTONE_ORE.getBlockData()
    };
    private boolean isOre(IBlockData mat) {
        for (IBlockData ore: NMS_ORES) {
            if (mat == ore)
                return true;
        }
        return false;
    }
    private static IBlockData setMaterialNMS = Blocks.STONE.getBlockData();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player player = (Player) sender;
        Chunk chunk = player.getLocation().getChunk();
        player.sendMessage("Your chunk: " + chunk.toString());
        StringBuilder message = new StringBuilder();

        long startTime = System.nanoTime();

        if (args.length == 0) {
            HashMap<String, Integer> materialData = scanChunk(chunk, (int)player.getLocation().getY());
            for (HashMap.Entry<String, Integer> entry : materialData.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                message.append(key).append(" : ").append(value).append("\n");
            }
        }
        else {
            String arg = args[0];
            int radius = 0;
            if (args.length > 1)
                try {
                    radius = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    player.sendMessage("Incorrect argument");
                    return false;
                }

            if (arg.equals("native")) {
                int[] data = setBlocksInSeveralNativeChunks(chunk, radius, player);
                message.append("Chunks changed: ").append(data[0]).append("\n");
                message.append("Blocks changed: ").append(data[1]);
            }
            if (arg.equals("nms")) {
                int[] data = setBlocksInSeveralNMSChunks(chunk, radius);
                message.append("Chunks changed: ").append(data[0]).append("\n");
                message.append("Blocks changed: ").append(data[1]);
            }
        }

        long endTime = System.nanoTime();
        long durationMS = (endTime - startTime) / 1_000;

        player.sendMessage("Blocks here:\n" + message);
        player.sendMessage("Execution time: " + durationMS + "micros");
        return true;

        //return false;
    }

    private HashMap<String, Integer> scanChunk(Chunk chunk, int height) {
        HashMap<String, Integer> materialData = new HashMap<>();
        int curHeight = chunk.getWorld().getMinHeight();
        Block[][] slice = new Block[16][16];
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                slice[x][z] = chunk.getBlock(x, curHeight, z);

        while (curHeight < height) {
            for (int x = 0; x < slice.length; x++) {
                for (int z = 0; z < slice[x].length; z++) {
                    Block block = slice[x][z];
                    String mat = block.getType().toString();
                    int curCnt = materialData.getOrDefault(mat, 0);
                    materialData.put(mat, curCnt + 1);
                }
            }
            for (int x = 0; x < slice.length; x++)
                for (int z = 0; z < slice[x].length; z++)
                    slice[x][z] = slice[x][z].getRelative(BlockFace.UP);
            curHeight++;
        }
        return materialData;
    }

    private int[] setBlocksInSeveralNativeChunks(Chunk curChunk, int radius, Player player) {
        int curX = curChunk.getX();
        int curZ = curChunk.getZ();
        int cntChunks = 0;
        int cntBlocks = 0;
        World world = curChunk.getWorld();
        for (int x = curX - radius; x <= curX + radius; x++) {
            for (int z = curZ - radius; z <= curZ + radius; z++) {
                Chunk chunk = world.getChunkAt(x, z);
                cntBlocks += setBLocksInNativeChunk(chunk);
                cntChunks++;
            }
        }
        return new int[]{cntChunks, cntBlocks};
    }

    private int setBLocksInNativeChunk(Chunk chunk) {
        int blocksChanged = 0;
        int curHeight = chunk.getWorld().getMinHeight();
        Block[][] slice = new Block[16][16];
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                slice[x][z] = chunk.getBlock(x, curHeight, z);

        while (curHeight < 128) {
            for (int x = 0; x < slice.length; x++) {
                for (int z = 0; z < slice[x].length; z++) {
                    Block block = slice[x][z];
                    Material mat = block.getType();
                    if (isOre(mat)) {
                        block.setType(setMaterial);
                        blocksChanged++;
                    }
                }
            }
            for (int x = 0; x < slice.length; x++)
                for (int z = 0; z < slice[x].length; z++)
                    slice[x][z] = slice[x][z].getRelative(BlockFace.UP);
            curHeight++;
        }
        return blocksChanged;
    }



    private int[] setBlocksInSeveralNMSChunks(Chunk curChunk, int radius) {
        int cntChunks = 0;
        int cntBlocks = 0;
        net.minecraft.server.v1_16_R3.Chunk nmsChunk = ((CraftChunk) curChunk).getHandle();
        net.minecraft.server.v1_16_R3.World world = nmsChunk.getWorld();
        ChunkCoordIntPair chunkPos = nmsChunk.getPos();
        for (int x = chunkPos.x - radius; x <= chunkPos.x + radius; x++) {
            for (int z = chunkPos.z - radius; z <= chunkPos.z + radius; z++) {
                cntBlocks += setBLocksInNMSChunk(world.getChunkAt(x, z));
                cntChunks++;
            }
        }
        return new int[]{cntChunks, cntBlocks};
    }

    private int setBLocksInNMSChunk(net.minecraft.server.v1_16_R3.Chunk chunk) {
        int blocksChanged = 0;
        ChunkCoordIntPair chunkPos = chunk.getPos();
        double curX = chunkPos.x << 4;
        double curY = 1;
        double curZ = chunkPos.z << 4;
        BlockPosition[][] slice = new BlockPosition[16][16];
        for (int x = 0; x < 16; x++)
            for (int z = 0; z < 16; z++)
                slice[x][z] = new BlockPosition(x + curX, curY, z + curZ);

        while (curY < 128) {
            for (int x = 0; x < slice.length; x++) {
                for (int z = 0; z < slice[x].length; z++) {
                    BlockPosition bp = slice[x][z];
                    IBlockData curData = chunk.getType(bp);
                    if (isOre(curData)) {
                        chunk.setType(bp, setMaterialNMS, false);
                        blocksChanged++;
                    }
                }
            }
            for (int x = 0; x < slice.length; x++)
                for (int z = 0; z < slice[x].length; z++)
                    slice[x][z] = slice[x][z].up();
            curY++;
        }
        return blocksChanged;
    }
}
