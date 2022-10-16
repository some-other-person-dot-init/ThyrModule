package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.sound.Sound;
import net.kyori.adventure.sound.Sound.Source;
import net.kyori.adventure.text.TextComponent;

import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
//import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.entity.Item;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.event.player.AsyncChatEvent;

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
		if (evt.getClickedBlock() != null && evt.getClickedBlock().getType().equals(Material.BARRIER) && evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getHand().equals(EquipmentSlot.HAND) && ThyrModule.printerBlockLocs.containsKey(evt.getClickedBlock().getLocation()))
		{
			if (!evt.getPlayer().isSneaking())
			{
				ThyrModule.printerBlockLocs.get(evt.getClickedBlock().getLocation()).openGUI(evt.getPlayer(), null);
				evt.setUseItemInHand(Result.DENY);
			}
			else
			{
				/*if (evt.getItem().equals(ThyrModule.printerItemAlt))
				{
					evt.setUseItemInHand(Result.DENY);
				}
				else if (evt.getItem().getType().equals(Material.PRISMARINE_SLAB) && evt.getBlockFace().equals(BlockFace.UP))
				{
					evt.getClickedBlock().getLocation().add(0, 1, 0).getBlock().setType(Material.PRISMARINE_SLAB);
					if (!evt.getPlayer().getGameMode().equals(GameMode.CREATIVE))
						evt.getItem().setAmount(evt.getItem().getAmount() - 1);
					evt.setUseItemInHand(Result.DENY);
				}*/
			}
		}
	}
	
	@EventHandler
	public void printerDownloadImg(AsyncChatEvent evt)
	{
		if (ThyrModule.inputPrinterMode.containsKey(evt.getPlayer().getUniqueId()) && evt.isAsynchronous())
		{
			Bukkit.getScheduler().runTask(ThyrModule.getPlugin(ThyrModule.class), () -> {
				Location loc = ThyrModule.inputPrinterMode.get(evt.getPlayer().getUniqueId()).getLocation();
				ThyrModule.inputPrinterMode.get(evt.getPlayer().getUniqueId()).openGUI(evt.getPlayer(), ((TextComponent) evt.originalMessage()).content());
				for (Player el : loc.getNearbyPlayers(6))
				{
					el.playSound(loc, "potolotcraft:item.printer.download", ThyrModule.soundDist(loc, evt.getPlayer().getLocation(), 6, 1.2), (float) 1);
				}
				ThyrModule.inputPrinterMode.remove(evt.getPlayer().getUniqueId());
			});
			evt.setCancelled(true);
		}
	}
}
