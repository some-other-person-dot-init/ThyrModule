package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;
import me.thyristor.thyrmodule.object.Printer;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockListeners implements Listener
{
	@EventHandler
	public void placePrinter(BlockPlaceEvent evt)
	{
		if (evt.getBlockPlaced().getType().equals(Material.PRISMARINE_SLAB) && ThyrModule.hasItemKey(evt.getItemInHand(), "printer") && !evt.getBlockAgainst().getType().equals(Material.PRISMARINE_SLAB))
		{
			evt.getPlayer().chat("OMG SO TRUE!");
			ThyrModule.printerBlockLocs.put(evt.getBlockPlaced().getLocation(), new Printer(evt.getBlockPlaced().getLocation()));
		}
	}
	
	@EventHandler
	public void breakPrinter(BlockBreakEvent evt)
	{
		if (evt.getBlock().getType().equals(Material.PRISMARINE_SLAB) && ThyrModule.printerBlockLocs.containsKey(evt.getBlock().getLocation()))
		{
			evt.setDropItems(false);
			if (!evt.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				ItemStack itmstk = new ItemStack(ThyrModule.printerItem);
				evt.getPlayer().getWorld().dropItem(evt.getBlock().getLocation(), itmstk);
			}
			ItemStack[] printInv = ThyrModule.printerBlockLocs.get(evt.getBlock().getLocation()).getInventory().getContents(); 
			for (ItemStack el : printInv)
			{
				if (el != null)
					evt.getPlayer().getWorld().dropItem(evt.getBlock().getLocation(), el);
			}
			ThyrModule.printerBlockLocs.remove(evt.getBlock().getLocation());
		}
	}
}
