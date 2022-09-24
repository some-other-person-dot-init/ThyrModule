package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ResourceProtocols;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ResCommand implements CommandExecutor
{	
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
					
					boolean code = new ResourceProtocols().stone(coords);
					
					if (code)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.GREEN + " [SUCCESS] Protocol has been completed successfully." + ChatColor.BOLD + ChatColor.GOLD + " CHECK the results!");
					}
					else
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [FAILURE] Protocol wasn't completed. Check the core of the protocol!");
					}
				}
				else if (arg[0].equals("oreo"))
				{
					if (arg.length != 7)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [ERROR] Check input data! Code: 01");
						return false;
					}
					
					snd.sendMessage(ChatColor.YELLOW + "|| Protocol \"OREO\" initiated...");
					double[] coords = new double[4];
					try 
					{
						for (int i = 0; i < 4; i++) coords[i] = Double.valueOf(arg[i+3]);
					}
					catch (NumberFormatException e)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [ERROR] Check input data! Code: 02");
						return false;
					}
					
					int intensity;
					try
					{
						intensity = Integer.valueOf(arg[2]);
					}
					catch (NumberFormatException e)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + "[ERROR] Check input data! Code: 03");
						return false;
					}
					
					boolean code = new ResourceProtocols().oreo(coords, arg[1], intensity);
					snd.sendMessage(coords[0] + " " + coords[1] + " " + coords[2] + " " + coords[3]);
					
					if (code)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.GREEN + " [SUCCESS] Protocol has been completed successfully." + ChatColor.BOLD + ChatColor.GOLD + " CHECK the results!");
					}
					else
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [FAILURE] Protocol wasn't completed. Check the core of the protocol!");
					}
				}
				else if (arg[0].equals("2137"))
				{
					if (arg.length != 5)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [ERROR] Check input data! Code: 01");
						return false;
					}
					
					snd.sendMessage(ChatColor.YELLOW + "|| Protocol \"2137\" initiated...");
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
					
					boolean code = new ResourceProtocols().janpawel2(coords);
					
					if (code)
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.GREEN + " [SUCCESS] Protocol has been completed successfully." + ChatColor.BOLD + ChatColor.GOLD + " CHECK the results!");
					}
					else
					{
						snd.sendMessage(ChatColor.YELLOW + "||" + ChatColor.RED + " [FAILURE] Protocol wasn't completed. Check the core of the protocol!");
					}
				}
				/*else if (arg[0].equals("papersplease"))
				{
					Location plyloc = Bukkit.getPlayer(snd.getName()).getLocation();
					new ResourceProtocols(this.plugin).checkIDs(plyloc);
				}*/
			}
			return true;
		}
		return false;
	}
}
