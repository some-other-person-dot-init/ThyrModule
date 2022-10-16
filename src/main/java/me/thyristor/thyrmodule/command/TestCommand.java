package me.thyristor.thyrmodule.command;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;

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
			else if (arg[0].equals("printer"))
			{
				if (!(snd instanceof Player))
				{
					snd.sendMessage("|| You must be a player to do this");
					return false;
				}
				
				Player ply = (Player) snd;
				
				/*ItemStack prnt = new ItemStack(Material.PRISMARINE_SLAB, 1);
				NBTItem nbti = new NBTItem(prnt);
				NBTCompound disp = nbti.addCompound("display");
				disp.setString("Name", "Printer Nordex IX-80");
				NBTCompound comp = nbti.addCompound("printerdata");
				comp.setBoolean("printer", true);*/
				
				//prnt = nbti.getItem();
				ply.getInventory().addItem(ThyrModule.printerItem);
				ply.getInventory().addItem(ThyrModule.printerItemAlt);
			}
			return true;
		}
		return false;
	}
	
	private void switchEnableDrop(final String name)
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
