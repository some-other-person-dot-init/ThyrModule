package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;

public class PlayerListeners implements Listener
{
	private ThyrModule plugin;
	
	public PlayerListeners(ThyrModule plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void ItemDropVerbot(PlayerDropItemEvent evt)
	{
		 Player ply = evt.getPlayer();
		 
		 if (plugin.enableDrop)
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
	public void CutsceneSneakVerbot(PlayerToggleSneakEvent evt)
	{
		Player ply = evt.getPlayer();
		
		evt.setCancelled(false);
;	}
}
