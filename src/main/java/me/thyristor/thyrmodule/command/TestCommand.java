package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;

public class TestCommand implements CommandExecutor 
{
	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String lbl, String[] arg)
	{
		if (snd instanceof Player && snd.hasPermission("thyrmodule.test") || !(snd instanceof Player))
		{
			if (arg.length < 1)
			{
				snd.sendMessage("|| It seems, that it works, кстати");
			}
			else if (arg[0].equals("tpr"))
			{
				if (!(snd instanceof Player))
				{
					snd.sendMessage("|| You must be a player to do this");
					return false;
				}
				
				Player ply = (Player) snd;
				
			}
			else if (arg[0].equals("dropverbot"))
			{
				if (!(snd instanceof Player))
				{
					snd.sendMessage("|| You must be a player to do this");
					return false;
				}
				
				Player ply = (Player) snd;
				
				this.switchEnableDrop(ply.getName());
				ply.sendMessage("|| You switched DropVerbot (" + ThyrModule.enableDrop + ")");
			}
			return true;
		}
		return false;
	}
	
	public void switchEnableDrop(String name)
	{
		if (ThyrModule.enableDrop)
		{
			ThyrModule.enableDrop = false;
			ThyrModule.playerListDVerbot.remove(name);
		}
		else
		{
			ThyrModule.enableDrop = true;
			ThyrModule.playerListDVerbot.put(name, true);
		}
	}
}
