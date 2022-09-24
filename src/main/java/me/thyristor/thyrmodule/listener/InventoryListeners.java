package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;
import me.thyristor.thyrmodule.handler.GuiHandler;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InventoryListeners implements Listener
{
	private boolean movedToOtherInv(InventoryClickEvent evt)
	{
		return evt.getAction().equals(InventoryAction.MOVE_TO_OTHER_INVENTORY) || 
				/*((evt.getAction().equals(InventoryAction.PLACE_ALL) ||
					evt.getAction().equals(InventoryAction.PLACE_ONE) ||
					evt.getAction().equals(InventoryAction.PLACE_SOME)) && 
					evt.getClickedInventory().equals(plugin.guiHandlerList.get(evt.getWhoClicked().getUniqueId()).getInventory()));*/
				evt.getClickedInventory().equals(ThyrModule.guiHandlerList.get(evt.getWhoClicked().getUniqueId()).getInventory());
	}
	
	@EventHandler
	public void guiClick(InventoryClickEvent evt)
	{
		if (ThyrModule.guiHandlerList.get(evt.getWhoClicked().getUniqueId()) != null && evt.getInventory().equals(ThyrModule.guiHandlerList.get(evt.getWhoClicked().getUniqueId()).getInventory()))
		{
			if (movedToOtherInv(evt))
			{
				evt.setCancelled(true);
			}
		}
	}
}
