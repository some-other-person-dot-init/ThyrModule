package me.thyristor.thyrmodule;

import java.util.concurrent.ThreadLocalRandom;
import java.util.HashMap;

import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.ChunkSection;
//import net.minecraft.server.v1_16_R3.World;
import net.minecraft.server.v1_16_R3.IBlockData;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.World;

// https://www.spigotmc.org/threads/methods-for-changing-massive-amount-of-blocks-up-to-14m-blocks-s.395868/
public class ResourceProtocols
{
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
	private HashMap<String,Integer> codeTypes = new HashMap<String,Integer>();
	private HashMap<Integer,Double> inteTypes = new HashMap<Integer,Double>();

	public ResourceProtocols()
	{
		this.codeTypes.put("opal", 71);
		this.codeTypes.put("maiden", 70);
		this.codeTypes.put("romania", 69);
		this.codeTypes.put("siemens", 3885);
		this.codeTypes.put("himmel", 232);	// do we need this?
		this.codeTypes.put("pinkfloyd", 3354);
		this.codeTypes.put("92", 5254);		// do we need this? x2
		
		this.inteTypes.put(71, 0.0151); // ???
		this.inteTypes.put(70, 0.0099); // 0.003906
		this.inteTypes.put(69, 0.0044); // 0.001465
		this.inteTypes.put(3885, 0.0013);
		this.inteTypes.put(232, 0.0014); // ???
		this.inteTypes.put(3354, 0.0005);
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
	
	public boolean oreo(double[] crd, String type, int intensity)
	{
		int code = 0;
		try
		{
			code = this.codeTypes.get(type);
		}
		catch (Exception e)
		{
			Bukkit.getLogger().warning("|| Error occured while initiating method: " + e);
			return false;
		}
		double inte = this.inteTypes.get(code) * (intensity / 100);
		double rand = 0.0;
		
		for (int x = (int)crd[0]; x <= (int)crd[2]; x++)
		{
			for (int z = (int)crd[1]; z <= (int)crd[3]; z++)
			{
				for (int y = 4; y <= 32; y++)
				{
					try
					{
						rand = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
						//Bukkit.getLogger().info(x + " " + y + " " + z);
						//Bukkit.getLogger().info(String.valueOf(rand) + " vs " + String.valueOf(inte));
						//Bukkit.getLogger().info(world.getBlockAt(x,y,z).getBlockData().getMaterial().toString());
						if ((world.getBlockAt(x,y,z).getBlockData().getMaterial().toString().equals("STONE")) && (rand <= inte))
						{
							//Bukkit.getLogger().info("|| Change block to " + type + " at " + x + ", " + y + ", " + z);
							setBlockEasy(world, x, y, z, code, (byte)0);
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
	
	public boolean janpawel2(double[] crd)
	{
		double inte = 0.014 * (300 / 100);
		double rand = 0.0;
		
		for (int x = (int)crd[0]; x <= (int)crd[2]; x++)
		{
			for (int z = (int)crd[1]; z <= (int)crd[3]; z++)
			{
				for (int y = 1; y <= 5; y++)
				{
					try
					{
						rand = ThreadLocalRandom.current().nextDouble(0.0, 1.0);
						//Bukkit.getLogger().info(x + " " + y + " " + z);
						//Bukkit.getLogger().info(String.valueOf(rand) + " vs " + String.valueOf(inte));
						//Bukkit.getLogger().info(world.getBlockAt(x,y,z).getBlockData().getMaterial().toString());
						if ((world.getBlockAt(x,y,z).getBlockData().getMaterial().toString().equals("STONE")) && (rand <= inte))
						{
							//Bukkit.getLogger().info("|| Change block to " + type + " at " + x + ", " + y + ", " + z);
							setBlockEasy(world, x, y, z, 6731, (byte)0);
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
	
	/*public void checkIDs(Location loc)
	{
		for (int id = 1; id++ <= 10000;)
		{
			setBlockEasy(world, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), id, (byte)0);
			if (loc.getBlock().getType().name().toLowerCase().indexOf("ore") != -1)
			{
				Bukkit.getLogger().info(id + ": " + loc.getBlock().getType().name());
				//Bukkit.getLogger().info("^ !!! ORE !!! ^");
			}
		}
	}*/
}
