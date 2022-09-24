package me.thyristor.thyrmodule.handler;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.InventoryView;

public class GuiHandler
{
	private Player ply;
	
	private Inventory inv;
	private InventoryHolder invHold;
	private InventoryView invView;
	private InventoryType invType;
	
	public GuiHandler(final Player ply)
	{
		this.ply = ply;
	}
	
	public void setInventory(final Inventory inv)
	{
		this.inv = inv;
		this.invType = this.inv.getType();
	}
	
	public void openInventory()
	{
		this.invView = this.ply.openInventory(inv);
	}
	
	public Inventory getInventory()
	{
		return this.inv;
	}
	
	public InventoryHolder getInventoryHolder()
	{
		return this.invHold;
	}
	
	public InventoryView getInventoryView()
	{
		return this.invView;
	}
	
	public InventoryType getType()
	{
		return this.invType;
	}
}
