package me.thyristor.thyrmodule.object;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.thyristor.thyrmodule.ThyrModule;
import net.kyori.adventure.text.Component;

import java.util.HashMap;

public class Printer
{
	@SuppressWarnings("hiding")
	@FunctionalInterface
	interface FunctionP<ItemStack, InventoryAction, Boolean>
	{
		public Boolean apply(ItemStack itm, InventoryAction act);
	}
	
	private Inventory inventory;
	private HashMap<Integer,ItemStack> items = new HashMap<Integer,ItemStack>();
	private Location location;
	private String link;
	private Gui gui;
	private int status;
	
	private HashMap<Integer,FunctionP <ItemStack, InventoryAction, Boolean>> invSlots = new HashMap<Integer,FunctionP <ItemStack, InventoryAction, Boolean>>();
	
	private String updateStatus()
	{
		switch (status)
		{
		case 0:
			return "§f🎈🎈🎈🎈🎈🎈🎈🎈🖨"; // IDLE
		case 1:
			return "§f🎈🎈🎈🎈🎈🎈🎈🎈⌨"; // NO CARTRIDGES
		case 2:
			return "§f🎈🎈🎈🎈🎈🎈🎈🎈🖱"; // DOWNLOAD SUCCESS
		case 3:
			return "§f🎈🎈🎈🎈🎈🎈🎈🎈🖲"; // DOWNLOAD FAIL
		case 4:
			return "§f🎈🎈🎈🎈🎈🎈🎈🎈💽"; // DONE
		}
		return "ERROR";
	}
	
	private void printContents(Inventory inv)
	{
		ItemStack[] itms = inv.getContents();
		for (ItemStack itm : itms)
		{
			if (itm != null)
				Bukkit.getLogger().info(itm.toString());
		}
	}
	
	private Gui setupGUI(Gui gui, Player ply, String link)
	{
		try
		{	
			gui.setOpenGuiAction(evt -> {
				Bukkit.getScheduler().runTaskLater(ThyrModule.getPlugin(ThyrModule.class), () -> loadInventory(), (long) 2);
			});
			
			gui.setCloseGuiAction(evt -> {
			});
			
			//gui.disableAllInteractions();
		}
		catch (Exception e)
		{
			Bukkit.getLogger().warning("Error while setting up GUI, see details: " + e.toString());
			return null;
		}
		
		return gui;
	}
	
	private Gui setupGUIItems(Gui gui, Player ply, String link)
	{
		GuiItem buttdownload = ItemBuilder.from(Material.MAP)
				.name(Component.text("§cЗагрузить изображение"))
				.lore(Component.text("§8§oПоддерживается §l.png, .jpg§r. §8§oОдна секция изображения: §n128x128"))
				.model(1010)
				.asGuiItem(evt -> {
					ThyrModule.inputPrinterMode.put(ply.getUniqueId(), this);
					gui.close(ply);
				});
		gui.setItem(26, buttdownload);
		
		GuiItem buttprint = ItemBuilder.from(Material.MAP)
				.name(Component.text("§2Печать"))
				.model(1010)
				.asGuiItem(evt -> {
					ply.sendMessage(link);
					status = 4;
					gui.updateTitle(updateStatus());
					
					for (Player el : location.getNearbyPlayers(6))
					{
						el.playSound(location, "potolotcraft:item.printer.print", ThyrModule.soundDist(location, el.getLocation(), 6), (float) 1);
					}
				});
		gui.setItem(44, buttprint);
		return gui;
	}
	
	public Printer(final Location location)
	{
		this.location = location;
		inventory = Bukkit.createInventory(null, 54);
		link = "";
		status = 0;
		invSlots.put(13, (itm, act) -> {
			return (act.equals(act.PLACE_ALL) || act.equals(act.PLACE_ONE) || act.equals(act.PLACE_SOME))
					&& itm.getType().equals(Material.PAPER)
					|| act.equals(act.PICKUP_ALL) || act.equals(act.PICKUP_HALF) || act.equals(act.PICKUP_SOME) || act.equals(act.PICKUP_ONE);
		});
		invSlots.put(18, (itm, act) -> {
			return (act.equals(act.PLACE_ALL) || act.equals(act.PLACE_ONE) || act.equals(act.PLACE_SOME)) 
					&& itm.getType().equals(Material.CYAN_DYE) 
					|| act.equals(act.PICKUP_ALL) || act.equals(act.PICKUP_HALF) || act.equals(act.PICKUP_SOME) || act.equals(act.PICKUP_ONE);
		});
		invSlots.put(27, (itm, act) -> {
			return (act.equals(act.PLACE_ALL) || act.equals(act.PLACE_ONE) || act.equals(act.PLACE_SOME)) 
					&& itm.getType().equals(Material.MAGENTA_DYE) 
					|| act.equals(act.PICKUP_ALL) || act.equals(act.PICKUP_HALF) || act.equals(act.PICKUP_SOME) || act.equals(act.PICKUP_ONE);
		});
		invSlots.put(36, (itm, act) -> {
			return (act.equals(act.PLACE_ALL) || act.equals(act.PLACE_ONE) || act.equals(act.PLACE_SOME)) 
					&& itm.getType().equals(Material.YELLOW_DYE) 
					|| act.equals(act.PICKUP_ALL) || act.equals(act.PICKUP_HALF) || act.equals(act.PICKUP_SOME) || act.equals(act.PICKUP_ONE);
		});
	}
	
	public void openGUI(Player ply, String link)
	{
		gui = Gui.gui().title(Component.text(updateStatus())).rows(6).create();
		
		gui = setupGUI(gui, ply, link);
		
		gui.open((Player) ply);
		
		gui = setupGUIItems(gui, ply, link);
		gui.disableOtherActions();
		gui.setDefaultTopClickAction(evt -> {
			ItemStack curitm = evt.getCursor();
			InventoryAction act = evt.getAction();
			if ( !(curitm != null && invSlots.containsKey(evt.getSlot()) && invSlots.get(evt.getSlot()).apply(curitm, act)
					|| ( act.equals(act.MOVE_TO_OTHER_INVENTORY) && invSlots.containsKey(evt.getSlot()) )
					|| (act.equals(act.CLONE_STACK) && evt.getSlot() != 26 && evt.getSlot() != 35 && evt.getSlot() != 44)) )
			{
				evt.setResult(Result.DENY);
				Bukkit.getLogger().info("DENY " + act.toString() + "\n" + evt.getClickedInventory().getType().toString());
			}
			else
			{
				Bukkit.getLogger().info("ALLOW " + act.toString() + "\n" + evt.getClickedInventory().getType().toString());
				saveInventory();
			}
			
			if (invSlots.containsKey(evt.getSlot()) && invSlots.get(evt.getSlot()).apply(curitm, act) && (evt.getSlot() == 18 || evt.getSlot() == 27 || evt.getSlot() == 36))
			{
				if (act.equals(act.PLACE_ALL) || act.equals(act.PLACE_ONE) || act.equals(act.PLACE_SOME))
				{
					evt.getWhoClicked().getWorld().playSound(location, "potolotcraft:item.printer.cartridge.place", (float) 1, (float) 1);
				}
				else if (act.equals(act.PICKUP_ALL) || act.equals(act.PICKUP_HALF) || act.equals(act.PICKUP_SOME) || act.equals(act.PICKUP_ONE))
				{
					evt.getWhoClicked().getWorld().playSound(location, "potolotcraft:item.printer.cartridge.remove", (float) 1, (float) 1);
				}
			}
		});
		gui.setDragAction(evt -> {
			//if
				evt.setResult(Result.DENY);
		});
		gui.update();
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	private void loadInventory()
	{
		//gui.getInventory().setContents(inventory.getContents());
		if (items.containsKey(13))
			gui.getInventory().setItem(13, items.get(13));
		if (items.containsKey(18))
			gui.getInventory().setItem(18, items.get(18));
		if (items.containsKey(27))
			gui.getInventory().setItem(27, items.get(27));
		if (items.containsKey(36))
			gui.getInventory().setItem(36, items.get(36));
		if (items.containsKey(49))
			gui.getInventory().setItem(49, items.get(49));
	}
	
	private void saveInventory()
	{
		//inventory.setContents(gui.getInventory().getContents());
		items.put(13, gui.getInventory().getItem(13));
		items.put(18, gui.getInventory().getItem(18));
		items.put(27, gui.getInventory().getItem(27));
		items.put(36, gui.getInventory().getItem(36));
		items.put(49, gui.getInventory().getItem(49));
	}
	
	public ItemStack getItem(int n)
	{
		return (items.containsKey(n)) ? items.get(n) : null;
	}
	
	public Location getLocation()
	{
		return location;
	}
	
	public int getStatus()
	{
		return status;
	}
}
