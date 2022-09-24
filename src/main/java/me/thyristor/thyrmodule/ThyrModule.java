package me.thyristor.thyrmodule;

import me.thyristor.thyrmodule.command.BloodikWayCommand;
import me.thyristor.thyrmodule.command.GuiCommand;
import me.thyristor.thyrmodule.command.ResCommand;
import me.thyristor.thyrmodule.command.TestCommand;
import me.thyristor.thyrmodule.command.TriGuiCommand;
import me.thyristor.thyrmodule.handler.GuiHandler;
import me.thyristor.thyrmodule.listener.BlockListeners;
import me.thyristor.thyrmodule.listener.EntityListeners;
import me.thyristor.thyrmodule.listener.InventoryListeners;
import me.thyristor.thyrmodule.listener.PlayerListeners;
import me.thyristor.thyrmodule.object.Printer;

import java.util.HashMap;
import java.util.UUID;

import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.TileState;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThyrModule extends JavaPlugin 
{
	static public boolean enableDrop = true;
	static public HashMap<String,Boolean> playerListDVerbot = new HashMap<String,Boolean>();
	static public HashMap<UUID,GuiHandler> guiHandlerList = new HashMap<UUID,GuiHandler>();
	static public HashMap<UUID,Inventory> invDataBase = new HashMap<UUID,Inventory>();
	static public HashMap<Location,Printer> printerBlockLocs = new HashMap<Location,Printer>();
	
	static public ItemStack printerItem;
	
	private boolean initItems()
	{
		printerItem = new ItemStack(Material.PRISMARINE_SLAB, 1);
		net.minecraft.server.v1_16_R3.ItemStack nmsstk = CraftItemStack.asNMSCopy(printerItem);
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInt("printer", 1);
		nmsstk.setTag(comp);
		printerItem = CraftItemStack.asBukkitCopy(nmsstk);
		
		return true;
	}
	
	@Override
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||--------------|>|'--------------||");
		//Bukkit.getConsoleSender().sendMessage("|| ThyrModule engaging...");
		
		this.getCommand("thyrtest").setExecutor(new TestCommand());
		//this.getCommand("res").setExecutor(new ResCommand());
		//this.getCommand("resbloodik").setExecutor(new BloodikWayCommand());
		this.getCommand("thyrgui").setExecutor(new GuiCommand());
		this.getCommand("trigui").setExecutor(new TriGuiCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new BlockListeners(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListeners(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new InventoryListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
		
		initItems();
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||" + ChatColor.WHITE + " ThyrModule engaged successfully!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "||--------------'|<|--------------||");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|| ThyrModule disabled!");
	}
	
	public static boolean hasItemKey(final ItemStack itmstk, final String key)
	{
		net.minecraft.server.v1_16_R3.ItemStack nmsstk = CraftItemStack.asNMSCopy(itmstk);
		return nmsstk.hasTag() && nmsstk.getTag().hasKey(key);
	}
}
