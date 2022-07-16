package me.thyristor.thyrmodule;

import me.thyristor.thyrmodule.ThyrModule;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChunkSection;
//import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.IBlockData;

import org.bukkit.Bukkit;
//import org.bukkit.Chunk;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
//import org.bukkit.Location;
import org.bukkit.World;

// https://www.spigotmc.org/threads/methods-for-changing-massive-amount-of-blocks-up-to-14m-blocks-s.395868/
public class ResourceProtocols
{
	private final ThyrModule plugin;
	
	private World world = Bukkit.getWorld("PotolotCraft");
	private String[] oreList = {
			"IRON_ORE",
			"REDSTONE_ORE",
			"EMERALD_ORE",
			"DIAMOND_ORE",
			"COAL_ORE",
			"GOLD_ORE",
			"LAPIS_ORE"
	};
	
	public ResourceProtocols(ThyrModule plugin)
	{
		this.plugin = plugin;
	}
	
	public boolean stone(double[] crd) // crd[] has 4 elements: Xstr, Zstr, Xend, Zend
	{
		for (int x = (int)crd[0]; x <= (int)crd[2]; x++)
		{
			for (int z = (int)crd[1]; z <= (int)crd[3]; z++)
			{
				for (int y = 1; y <= 100; y++)
				{
					try
					{
						for (String el : oreList)
						if (world.getBlockAt(x,y,z).getBlockData().getMaterial().toString().equals(el))
						{
							//Bukkit.getLogger().info("|| Change block " + el + " to stone at " + x + ", " + y + ", " + z);
							setBlockEasy(world, x, y, z, 1, (byte)0);
							//setBlockUltimate(world, x, y, z, 1, (byte)0);
						}
					}
					catch (Exception e)
					{
						Bukkit.getLogger().warning("|| Error occured while completing setBlock method: " + e);
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private static void setBlockEasy(World world, int x, int y, int z, int blockID, byte data)
	{
		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
	    BlockPosition bp = new BlockPosition(x, y, z);
	    IBlockData ibd = net.minecraft.server.v1_16_R3.Block.getByCombinedId(blockID + (data << 12));
	    nmsWorld.setTypeAndData(bp, ibd, 2);
	}
	
	private static void setBlockUltimate(World world, int x, int y, int z, int blockID, byte data)
	{
		net.minecraft.server.v1_16_R3.World nmsWorld = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_16_R3.Chunk nmsChunk = nmsWorld.getChunkAt(x >> 4, z >> 4);
		nmsChunk.loadCallback();
		IBlockData ibd = net.minecraft.server.v1_16_R3.Block.getByCombinedId(blockID + (data << 12));

		ChunkSection cs = nmsChunk.getSections()[y >> 4];
		if (cs == nmsChunk.a()) 
		{
			cs = new ChunkSection(y >> 4 << 4);
		    nmsChunk.getSections()[y >> 4] = cs;
		}

		cs.getBlocks().b(x & 15, y & 15, z & 15, ibd);
	}
}
