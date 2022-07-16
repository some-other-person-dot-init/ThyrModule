package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ThyrModule;
import me.thyristor.thyrmodule.ResourceProtocols;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResCommand implements CommandExecutor
{
	private final ThyrModule plugin;
	
	public ResCommand(ThyrModule plugin)
	{
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender snd, Command cmd, String lbl, String[] arg)
	{
		if (snd.hasPermission("thyrmodule.res"))
		{
			if (arg.length < 1)
			{
				snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.GOLD + " IF YOU SEE THIS MESSAGE, IT MEANS THAT EVERYTHING IS BAD." + ChatColor.BOLD + " CONTACT ADMINISTRATION IMMIDEATLY," + ChatColor.RESET + ChatColor.RED + " OTHERWISE WE ARE ALL DOOMED INCLUDING YOU!!!");
			}
			else
			{
				if (arg[0].equals("stone"))
				{
					if (arg.length != 5)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [ERROR] Check input data! Code: 01");
						return false;
					}
					
					snd.sendMessage(ChatColor.YELLOW + "|| Protocol \"STONE\" initiated...");
					double[] coords = new double[4];
					try 
					{
						for (int i = 0; i < 4; i++) coords[i] = Double.valueOf(arg[i+1]);
					}
					catch (NumberFormatException e)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [ERROR] Check input data! Code: 02");
						return false;
					}
					
					boolean code = new ResourceProtocols(this.plugin).stone(coords);
					
					if (code)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.GREEN + " [SUCCESS] Protocol has been completed successfully." + ChatColor.BOLD + ChatColor.GOLD + " CHECK the results!");
					}
					else
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [FAILURE] Protocol wasn't completed. Check the core of the protocol!");
					}
				}
			}
			return true;
		}
		return false;
	}
}
