package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;
import me.thyristor.thyrmodule.object.Printer;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BlockListeners implements Listener
{
	@EventHandler
	public void placePrinter(BlockPlaceEvent evt)
	{
		Bukkit.getLogger().info(evt.getBlockAgainst().toString());
		if (evt.getBlockPlaced().getType().equals(Material.ITEM_FRAME) && ThyrModule.hasItemKey(evt.getItemInHand(), "printer") && !evt.getBlockAgainst().getType().equals(Material.PRISMARINE_SLAB))
		{
			evt.getPlayer().sendMessage("OMG SO TRUE!");
			ThyrModule.printerBlockLocs.put(evt.getBlockPlaced().getLocation(), new Printer(evt.getBlockPlaced().getLocation()));
		}
	}
	
	/*@EventHandler
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
	}*/
	
	@EventHandler
	public void blockInteract(PlayerInteractEvent evt)
	{
		if (evt.getPlayer().getGameMode().equals(GameMode.SPECTATOR)) return;
		if ((evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) || evt.getAction().equals(Action.RIGHT_CLICK_AIR)) && evt.getHand().equals(EquipmentSlot.HAND))
			evt.getPlayer().sendMessage(evt.getAction().toString() + " " + evt.getClickedBlock() + " " + evt.getItem());
		
		// PRINTER PLACE
		if (evt.getAction().equals(Action.RIGHT_CLICK_BLOCK) && evt.getHand().equals(EquipmentSlot.HAND)
				&& evt.hasItem() && evt.getItem().getType().equals(Material.ITEM_FRAME) && ThyrModule.hasItemKey(evt.getItem(), "printer"))
		{
			if (!evt.getBlockFace().equals(BlockFace.UP) || evt.getClickedBlock().getType().equals(Material.BARRIER)) 
			{
				evt.setUseItemInHand(Result.DENY);
			}
			else
			{
				evt.getPlayer().sendMessage("§6OMG!!! THAT IS SOOO TRUE!!!");
				ThyrModule.printerBlockLocs.put(evt.getClickedBlock().getLocation().add(0, 1, 0), new Printer(evt.getClickedBlock().getLocation().add(0, 1, 0)));
			}
		}
		
		// PRINTER BREAK
		if (evt.getAction().equals(Action.LEFT_CLICK_BLOCK) && evt.getPlayer().isSneaking()
				&& evt.getClickedBlock() != null && evt.getClickedBlock().getType().equals(Material.BARRIER) && ((ItemFrame) evt.getClickedBlock().getLocation().getNearbyEntitiesByType(ItemFrame.class, 1).toArray()[0]).getItem().getItemMeta().getCustomModelData() == 1) 
		{
			World wrl = evt.getPlayer().getWorld();
			Location loc = evt.getClickedBlock().getLocation();
			Printer prinv = ThyrModule.printerBlockLocs.get(loc);
			
			if (!evt.getPlayer().getGameMode().equals(GameMode.CREATIVE))
			{
				wrl.dropItem(loc, new ItemStack(ThyrModule.printerItemAlt));
			}
			
			wrl.playSound(loc, Sound.ITEM_ARMOR_EQUIP_GENERIC, SoundCategory.BLOCKS, 1, (float) 1.5);
			
			if (prinv.getItem(13) != null) wrl.dropItem(loc, prinv.getItem(13));
			if (prinv.getItem(18) != null) wrl.dropItem(loc, prinv.getItem(18));
			if (prinv.getItem(27) != null) wrl.dropItem(loc, prinv.getItem(27));
			if (prinv.getItem(36) != null) wrl.dropItem(loc, prinv.getItem(36));
			
			loc.getBlock().setType(Material.AIR);
			((ItemFrame) evt.getClickedBlock().getLocation().getNearbyEntitiesByType(ItemFrame.class, 1).toArray()[0]).remove();
			
			ThyrModule.printerBlockLocs.remove(loc);
		}
	}
}
