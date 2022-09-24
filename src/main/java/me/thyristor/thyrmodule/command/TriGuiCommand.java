package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;

public class TriGuiCommand implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String lbl, String[] arg)
	{
		Bukkit.getLogger().info("Run trigui...");
		if (snd instanceof Player && snd.hasPermission("thyrmodule.trigui"))
		{
			Gui gui = Gui.gui().title(Component.text("§f🎈🎈🎈🎈🎈🎈🎈🎈🖨")).rows(6).create();
			gui.setOpenGuiAction(event -> {
				gui.getInventory().setContents(ThyrModule.invDataBase.get(((Player) snd).getUniqueId()).getContents());
			});
			gui.setCloseGuiAction(event -> {
				ThyrModule.invDataBase.put(((Player) snd).getUniqueId(), gui.getInventory());
			});
			gui.open((Player) snd);
			return true;
		}
		return false;
	}
}