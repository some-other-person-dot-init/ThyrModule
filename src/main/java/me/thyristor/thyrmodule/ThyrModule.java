package me.thyristor.thyrmodule;

import me.thyristor.thyrmodule.command.BloodikWayCommand;
import me.thyristor.thyrmodule.command.GuiCommand;
import me.thyristor.thyrmodule.command.ResCommand;
import me.thyristor.thyrmodule.command.TestCommand;
import me.thyristor.thyrmodule.command.TriGuiCommand;
import me.thyristor.thyrmodule.handler.GuiHandler;
import me.thyristor.thyrmodule.listener.BlockListeners;
import me.thyristor.thyrmodule.listener.EntityListeners;
import me.thyristor.thyrmodule.listener.InventoryListeners;
import me.thyristor.thyrmodule.listener.PlayerListeners;
import me.thyristor.thyrmodule.object.Printer;

import java.lang.Math;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import net.kyori.adventure.text.Component;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import net.minecraft.server.v1_16_R3.NBTTagList;
import net.minecraft.server.v1_16_R3.NBTTagString;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

public final class ThyrModule extends JavaPlugin 
{
	static public boolean enableDrop = true;
	
	static public HashMap<String,Boolean> playerListDVerbot = new HashMap<String,Boolean>();
	static public HashMap<UUID,GuiHandler> guiHandlerList = new HashMap<UUID,GuiHandler>();
	static public HashMap<UUID,Inventory> invDataBase = new HashMap<UUID,Inventory>();
	static public HashMap<Location,Printer> printerBlockLocs = new HashMap<Location,Printer>();
	static public HashMap<UUID,Printer> inputPrinterMode = new HashMap<UUID,Printer>();
	
	static public ItemStack printerItem;
	static public ItemStack printerItemAlt;
	
	//static public CustomBlockRegistry cbRegistry;
	
	private boolean initItems()
	{
		/* Printer Block
		 * Nordex IX-80
		 * Replaced: Prismarine slab
		 */
		/*printerItem = new ItemStack(Material.PRISMARINE_SLAB, 1);
		net.minecraft.server.v1_16_R3.ItemStack nmsstk = CraftItemStack.asNMSCopy(printerItem);
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInt("printer", 1);
		nmsstk.setTag(comp);
		printerItem = CraftItemStack.asBukkitCopy(nmsstk);
		ItemMeta itmmeta = printerItem.getItemMeta();
		itmmeta.setDisplayName("Принтер");
		itmmeta.setLore(Arrays.asList("§8§oNordex IX-80"));
		itmmeta.setCustomModelData(1); //Когда модель будет
		printerItem.setItemMeta(itmmeta);*/
		
		/* Printer Block Alt
		 * Nordex IX-80
		 * Replaced: Item frame
		 */
		printerItemAlt = new ItemStack(Material.ITEM_FRAME, 1);
		net.minecraft.server.v1_16_R3.ItemStack nmsstk = CraftItemStack.asNMSCopy(printerItemAlt);
		NBTTagCompound comp = new NBTTagCompound();
		comp.setInt("printer", 1);
		NBTTagCompound ucomp = new NBTTagCompound();
		ucomp.setBoolean("Silent", true);
		NBTTagList lst = new NBTTagList(); 
		lst.add(NBTTagString.a("printer"));
		ucomp.set("Tags", lst);
		NBTTagCompound fritem = new NBTTagCompound();
		fritem.setString("prikol", "lol");
		fritem.setString("id", "minecraft:item_frame");
		fritem.setInt("Count", 1);
		NBTTagCompound fritemtag = new NBTTagCompound();
		fritemtag.setInt("CustomModelData", 1);
		fritem.set("tag", fritemtag);
		ucomp.set("Item", fritem);
		ucomp.setBoolean("Invulnerable", true);
		ucomp.setBoolean("Invisible", true);
		ucomp.setBoolean("Fixed", true);
		comp.set("EntityTag", ucomp);
		nmsstk.setTag(comp);
		printerItemAlt = CraftItemStack.asBukkitCopy(nmsstk);
		ItemMeta itmmeta = printerItemAlt.getItemMeta();
		itmmeta.displayName(Component.text("Принтер АЛЬТ"));
		itmmeta.lore(Arrays.asList(Component.text("§8§oNordex IX-80")));
		itmmeta.setCustomModelData(1); //Когда модель будет
		printerItemAlt.setItemMeta(itmmeta);
		
		return true;
	}
	
	@Override
	public void onEnable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||--------------|>|'--------------||");
		//Bukkit.getConsoleSender().sendMessage("|| ThyrModule engaging...");
		
		this.getCommand("thyrtest").setExecutor(new TestCommand());
		//this.getCommand("res").setExecutor(new ResCommand());
		//this.getCommand("resbloodik").setExecutor(new BloodikWayCommand());
		this.getCommand("thyrgui").setExecutor(new GuiCommand());
		this.getCommand("trigui").setExecutor(new TriGuiCommand());
		Bukkit.getServer().getPluginManager().registerEvents(new BlockListeners(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new EntityListeners(), this);
		//Bukkit.getServer().getPluginManager().registerEvents(new InventoryListeners(this), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
		
		//cbRegistry = new CustomBlockRegistry(BlockDataManager.createAuto(this, this.getDataFolder().toPath().resolve("blocks.db"), true, true), this);
		initItems();			
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + "||" + ChatColor.WHITE + " ThyrModule engaged successfully!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "||--------------'|<|--------------||");
	}
	
	@Override
	public void onDisable()
	{
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "|| ThyrModule disabled!");
	}
	
	public static boolean hasItemKey(final ItemStack itmstk, final String key)
	{
		//Bukkit.getLogger().info("GOT IT!");
		net.minecraft.server.v1_16_R3.ItemStack nmsstk = CraftItemStack.asNMSCopy(itmstk);
		return nmsstk.hasTag() && nmsstk.getTag().hasKey(key);
	}
	
	// Distance between location 1 and location 2
	public static double locDist(Location l1, Location l2)
	{
		return Math.sqrt( Math.pow(Math.abs(l2.getX() - l1.getX()), 2) + Math.pow(Math.abs(l2.getY() - l1.getY()), 2) + Math.pow(Math.abs(l2.getZ() - l1.getZ()), 2) );
	}
	
	public static double comp(double mn, double mx, double a)
	{
		return Math.max(mn, Math.min(a, mx));
	}
	
	// Distance used for a relative volume of a sound
	public static float soundDist(Location src, Location lis, double threshold)
	{
		return (float) comp(0, 1, -locDist(src, lis) / threshold + 1);
	}
	public static float soundDist(Location src, Location lis, double threshold, double amp)
	{
		return (float) comp(0, 1, amp*(-locDist(src, lis) / threshold + 1));
	}
}
