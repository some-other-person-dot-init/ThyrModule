package me.thyristor.thyrmodule;

import me.thyristor.thyrmodule.command.BloodikWayCommand;
import me.thyristor.thyrmodule.command.GuiCommand;
import me.thyristor.thyrmodule.command.ResCommand;
import me.thyristor.thyrmodule.command.TestCommand;
import me.thyristor.thyrmodule.command.TriGuiCommand;
import me.thyristor.thyrmodule.handler.GuiHandler;
import me.thyristor.thyrmodule.listener.EntityListeners;
import me.thyristor.thyrmodule.listener.InventoryListeners;
import me.thyristor.thyrmodule.listener.PlayerListeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThyrModule extends JavaPlugin 
{
	static public boolean enableDrop = true;
	static public HashMap<String,Boolean> playerListDVerbot = new HashMap<String,Boolean>();
	static public HashMap<UUID,GuiHandler> guiHandlerList = new HashMap<UUID,GuiHandler>();
	static public HashMap<UUID,Inventory> invDataBase = new HashMap<UUID,Inventory>();
	
	@Override
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||--------------|>|'--------------||");
		//Bukkit.getConsoleSender().sendMessage("|| ThyrModule engaging...");
		
		this.getCommand("thyrtest").setExecutor(new TestCommand());
		this.getCommand("res").setExecutor(new ResCommand());
		this.getCommand("resbloodik").setExecutor(new BloodikWayCommand());
		this.getCommand("thyrgui").setExecutor(new GuiCommand());
		this.getCommand("trigui").setExecutor(new TriGuiCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListeners(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new InventoryListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||" + ChatColor.WHITE + " ThyrModule engaged successfully!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "||--------------'|<|--------------||");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|| ThyrModule disabled!");
	}
}
