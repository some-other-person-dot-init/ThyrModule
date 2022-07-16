package me.thyristor.thyrmodule;

import me.thyristor.thyrmodule.command.BloodikWayCommand;
import me.thyristor.thyrmodule.command.ResCommand;
import me.thyristor.thyrmodule.command.TestCommand;
import me.thyristor.thyrmodule.listener.EntityListeners;
import me.thyristor.thyrmodule.listener.PlayerListeners;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThyrModule extends JavaPlugin 
{
	public boolean enableDrop = true;
	public HashMap<String,Boolean> playerListDVerbot = new HashMap<String,Boolean>();
	
	@Override
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||--------------|>|'--------------||");
		//Bukkit.getConsoleSender().sendMessage("|| ThyrModule engaging...");
		
		this.getCommand("thyrtest").setExecutor(new TestCommand(this));
		this.getCommand("res").setExecutor(new ResCommand(this));
		this.getCommand("resbloodik").setExecutor(new BloodikWayCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListeners(this), this);
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||" + ChatColor.WHITE + " ThyrModule engaged successfully!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "||--------------'|<|--------------||");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "§c|| ThyrModule disabled!");
	}
}
