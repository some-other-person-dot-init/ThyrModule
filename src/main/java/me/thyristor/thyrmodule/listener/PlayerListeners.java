package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Material;
//import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener
{
	@EventHandler
	public void ItemDropVerbot(PlayerDropItemEvent evt)
	{
		 Player ply = evt.getPlayer();
		 
		 if (ThyrModule.enableDrop)
		 {
			 evt.setCancelled(false);
		 }
		 else
		 {
			 evt.setCancelled(true);
			 ply.sendMessage("Duuuude, you're not allowed to drop");
			 Item itm = evt.getItemDrop();
			 ItemStack itmstk = itm.getItemStack();
			 ply.sendMessage(itmstk.toString());
		 }
	}
	
	@EventHandler
	public void printerInteract(PlayerInteractEvent evt)
	{
		if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getClickedBlock().getBlockData().getMaterial().equals(Material.PRISMARINE_SLAB) && ThyrModule.printerBlockLocs.containsKey(evt.getClickedBlock().getLocation()))
		{
			ThyrModule.printerBlockLocs.get(evt.getClickedBlock().getLocation()).openGUI(evt.getPlayer());
			evt.setUseItemInHand(Result.DENY);
		}
	}
	
	/*@EventHandler
	public void CutsceneSneakVerbot(PlayerToggleSneakEvent evt)
	{
		Player ply = evt.getPlayer();
		
		evt.setCancelled(false);
	}*/
}
