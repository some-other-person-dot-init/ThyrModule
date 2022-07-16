package me.thyristor.thyrmodule.listener;

import me.thyristor.thyrmodule.ThyrModule;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import com.denizenscript.denizen.events.entity.EntitySpawnScriptEvent;
import com.denizenscript.denizen.objects.EntityTag;
import com.denizenscript.denizen.utilities.*;
import com.denizenscript.denizen.utilities.entity.DenizenEntityType;
import com.denizenscript.denizencore.objects.Mechanism;
import com.denizenscript.denizencore.objects.ObjectTag;
import com.denizenscript.denizencore.scripts.ScriptEntryData;
import com.denizenscript.denizencore.scripts.ScriptRegistry;
//import com.denizenscript.denizencore.flags.MapTagFlagTracker;
//import com.denizenscript.denizen.objects.properties.entity.EntityFlags;
import com.denizenscript.denizencore.scripts.containers.ScriptContainer;

public class EntityListeners implements Listener
{
	private ThyrModule plugin;
	public EntityListeners(ThyrModule plugin)
	{
		this.plugin = plugin;
	}
	
	/* FIXME
	* This was supposed to be an interaction implementation between Java and Denizen entities,
	* but because tags on Denizen are working, let's just say, weirdly (they are not visible on Java),
	* I can't seem to make it work (That's why almost everything is commented)
	* (Do I even need it?)
	*/
	@EventHandler
	public void CheckArmorStand(EntitySpawnEvent evt)
	{
		Entity ent = evt.getEntity();
		//EntityTag tag = new EntityTag(ent);//EntityTag.valueOf("e@" + String.valueOf(ent.getUniqueId()) + "/", null);
		//Bukkit.getLogger().info(ScriptRegistry.getScriptContainer("Testilka").getContents().contents.toString());
		
		/*for (Mechanism el: tag.mechanisms)
			Bukkit.getLogger().info(el.toString());*/
		
		if (String.valueOf(ent.getType()).equals("ARMOR_STAND") /*&& ent.hasMetadata("has_ai") && ent.getMetadata("Cutscene")*/)
		{
			//ent.setMetadata("flag_map", new FixedMetadataValue(this.plugin, true));
			//Bukkit.getLogger().info("YES!\n"/* + ent.getMetadata("has_ai").get(0).asBoolean()*/);
		}
	}
	
	/*@EventHandler
	public void CheckStandTag(EntitySpawnScriptEvent evt)
	{
		ObjectTag ent = evt.getContext("entity");
		for (String el: ((EntityTag) ent).getFlagTrackerForTag().listAllFlags())
			Bukkit.getLogger().info(el);
	}*/
}
