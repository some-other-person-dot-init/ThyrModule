package me.thyristor.thyrmodule.object;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import dev.triumphteam.gui.guis.Gui;
import net.kyori.adventure.text.Component;

public class Printer
{
	private Inventory inventory;
	private Location location;
	
	public Printer(final Location location)
	{
		this.location = location;
		inventory = Bukkit.createInventory(null, 54);
	}
	
	public void openGUI(Player ply)
	{
		Gui gui = Gui.gui().title(Component.text("§f🎈🎈🎈🎈🎈🎈🎈🎈🖨")).rows(6).create();
		gui.setOpenGuiAction(event -> {
			gui.getInventory().setContents(inventory.getContents());
		});
		gui.setCloseGuiAction(event -> {
			inventory.setContents(gui.getInventory().getContents());
		});
		gui.open((Player) ply);
	}
	
	public Inventory getInventory()
	{
		return inventory;
	}
	
	public Location getLocation()
	{
		return location;
	}
}
