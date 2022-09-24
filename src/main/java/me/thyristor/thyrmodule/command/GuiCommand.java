package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ThyrModule;
import me.thyristor.thyrmodule.handler.GuiHandler;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class GuiCommand implements CommandExecutor
{
	private Inventory plyinv;
	private InventoryView plyinvview;
	
	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String lbl, String[] arg)
	{
		Bukkit.getLogger().info("Run thyrgui...");
		if (snd instanceof Player && snd.hasPermission("thyrmodule.thyrgui"))
		{
			snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.WHITE + " GUI ACTIVATED");
			
			ThyrModule.guiHandlerList.put(((Player) snd).getUniqueId(), new GuiHandler((Player) snd));
			GuiHandler gui = ThyrModule.guiHandlerList.get(((Player) snd).getUniqueId());
			gui.setInventory(Bukkit.createInventory((InventoryHolder) snd, 54));
			gui.openInventory();
			return true;
		}
		return false;
	}
}
