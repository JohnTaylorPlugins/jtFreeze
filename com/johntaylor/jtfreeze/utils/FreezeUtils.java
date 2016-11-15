package com.johntaylor.jtfreeze.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.PluginManager;

import com.johntaylor.jtfreeze.Main;

import net.md_5.bungee.api.ChatColor;

public class FreezeUtils
  implements Listener
{
  private static Map<String, FreezeUtils> freezes = new HashMap();
  private String name;
  private Player p;
  private boolean cancelled;
  private Location freeze;
  
  public static FreezeUtils getFreeze(Player p)
  {
    return (FreezeUtils)freezes.get(p.getName());
  }
  
  public static boolean hasFreeze(Player p)
  {
    if (freezes.containsKey(p.getName()))
    {
      if (!((FreezeUtils)freezes.get(p.getName())).isCancelled()) {
        return true;
      }
      freezes.remove(p.getName());
      return false;
    }
    return false;
  }
  
  public static void removeFreeze(Player p)
  {
    if (freezes.containsKey(p.getName())) {
      if (hasFreeze(p))
      {
        ((FreezeUtils)freezes.get(p.getName())).setCancelled(true);
        freezes.remove(p.getName());
      }
      else
      {
        freezes.remove(p.getName());
      }
    }
  }
  
  public FreezeUtils(Player p)
  {
    this.p = p;
    this.name = p.getName();
    this.cancelled = false;
    this.freeze = p.getLocation();
    freezes.put(p.getName(), this);
  }
  
  public FreezeUtils(Player p, Location freeze)
  {
    this.p = p;
    this.name = p.getName();
    this.cancelled = false;
    this.freeze = freeze;
    freezes.put(p.getName(), this);
  }
  
  public void run()
  {
    Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
    for (Location loc : getWallBlocks(this.freeze)) {
      sendWallBlock(this.p, loc);
    }
    for(Location loc : getNetherPortalBlocks(this.freeze)) {
    	sendNetherPortalBlock(this.p, loc);
    }
  }
  
  public List<Location> getWallBlocks(Location loc)
  {
    Location l = loc.clone();
    
    List<Location> locations = new ArrayList();
    locations.add(l.clone().subtract(0.0D, 1.0D, 0.0D)); //1
    locations.add(l.clone().subtract(1.0D, 1.0D, 0.0D)); //2
    locations.add(l.clone().subtract(-1.0D, 1.0D, 0.0D)); //3
    locations.add(l.clone().subtract(1.0D, 1.0D, 1.0D)); //4
    locations.add(l.clone().subtract(0.0D, 1.0D, 1.0D)); //5
    locations.add(l.clone().subtract(1.0D, 1.0D, 1.0D)); //6
    locations.add(l.clone().subtract(-1.0D, 1.0D, 1.0D)); //7
    locations.add(l.clone().subtract(-1.0D, 1.0D, -1.0D)); //8
    locations.add(l.clone().subtract(1.0D, 1.0D, -1.0D)); //9
    locations.add(l.clone().subtract(0.0D, 1.0D, -1.0D)); //10
    locations.add(l.clone().subtract(-1.0D, 1.0D, 0.0D)); //11 
    
    locations.add(l.clone().add(0.0D, 2.0D, 0.0D)); //1
    locations.add(l.clone().add(1.0D, 2.0D, 0.0D)); //2
    locations.add(l.clone().add(1.0D, 2.0D, 1.0D)); //3
    locations.add(l.clone().add(-1.0D, 2.0D, 0.0D)); //4
    locations.add(l.clone().add(-1.0D, 2.0D, 1.0D)); //5
    locations.add(l.clone().add(0.0D, 2.0D, 1.0D)); //6
    locations.add(l.clone().add(0.0D, 2.0D, -1.0D)); //7
    locations.add(l.clone().add(1.0D, 2.0D, -1.0D)); //8
    locations.add(l.clone().add(-1.0D, 2.0D, -1.0D)); //9
    
    locations.add(l.clone().add(2.0D, 1.0D, 0.0D)); //1
    locations.add(l.clone().add(2.0D, 1.0D, 1.0D)); //2
    locations.add(l.clone().add(2.0D, 1.0D, -1.0D)); //3
    
    locations.add(l.clone().clone().add(0.0D, 1.0D, 2.0D)); //1
    locations.add(l.clone().clone().add(1.0D, 1.0D, 2.0D)); //2
    locations.add(l.clone().clone().add(-1.0D, 1.0D, 2.0D)); //3
    
    locations.add(l.clone().add(-2.0D, 1.0D, 0.0D)); //1
    locations.add(l.clone().add(-2.0D, 1.0D, 1.0D)); //2
    locations.add(l.clone().add(-2.0D, 1.0D, -1.0D)); //3
    
    locations.add(l.clone().add(0.0D, 1.0D, -2.0D)); //1
    locations.add(l.clone().add(1.0D, 1.0D, -2.0D)); //2
    locations.add(l.clone().add(-1.0D, 1.0D, -2.0D)); //3
    
    locations.add(l.clone().add(2.0D, 0.0D, 0.0D)); //1
    locations.add(l.clone().add(2.0D, 0.0D, 1.0D)); //2
    locations.add(l.clone().add(2.0D, 0.0D, -1.0D)); //3
    
    locations.add(l.clone().clone().add(0.0D, 0.0D, 2.0D)); //1
    locations.add(l.clone().clone().add(1.0D, 0.0D, 2.0D)); //2
    locations.add(l.clone().clone().add(-1.0D, 0.0D, 2.0D)); //3
    
    locations.add(l.clone().add(-2.0D, 0.0D, 0.0D)); //1
    locations.add(l.clone().add(-2.0D, 0.0D, 1.0D)); //2
    locations.add(l.clone().add(-2.0D, 0.0D, -1.0D)); //3
    
    locations.add(l.clone().add(0.0D, 0.0D, -2.0D)); //1
    locations.add(l.clone().add(1.0D, 0.0D, -2.0D)); //2
    locations.add(l.clone().add(-1.0D, 0.0D, -2.0D)); //3
    
    return locations;
  }
  
  public List<Location> getNetherPortalBlocks(Location loc)
  {
    Location l = loc.clone();
    
    List<Location> locations = new ArrayList();
    locations.add(l.clone().subtract(0.0D, 0.0D, 0.0D)); //1
    locations.add(l.clone().subtract(1.0D, 0.0D, 0.0D)); //2
    locations.add(l.clone().subtract(-1.0D, 0.0D, 0.0D)); //3
    locations.add(l.clone().subtract(1.0D, 0.0D, 1.0D)); //4
    locations.add(l.clone().subtract(0.0D, 0.0D, 1.0D)); //5
    locations.add(l.clone().subtract(1.0D, 0.0D, 1.0D)); //6
    locations.add(l.clone().subtract(-1.0D, 0.0D, 1.0D)); //7
    locations.add(l.clone().subtract(-1.0D, 0.0D, -1.0D)); //8
    locations.add(l.clone().subtract(1.0D, 0.0D, -1.0D)); //9
    locations.add(l.clone().subtract(0.0D, 0.0D, -1.0D)); //10
    locations.add(l.clone().subtract(-1.0D, 0.0D, 0.0D)); //11 
    
    locations.add(l.clone().add(0.0D, 1.0D, 0.0D)); //1
    locations.add(l.clone().add(1.0D, 1.0D, 0.0D)); //2
    locations.add(l.clone().add(1.0D, 1.0D, 1.0D)); //3
    locations.add(l.clone().add(-1.0D, 1.0D, 0.0D)); //4
    locations.add(l.clone().add(-1.0D, 1.0D, 1.0D)); //5
    locations.add(l.clone().add(0.0D, 1.0D, 1.0D)); //6
    locations.add(l.clone().add(0.0D, 1.0D, -1.0D)); //7
    locations.add(l.clone().add(1.0D, 1.0D, -1.0D)); //8
    locations.add(l.clone().add(-1.0D, 1.0D, -1.0D)); //9
    
    return locations;
  }
  
  public static void sendNetherPortalBlock(Player p, Location loc)
  {
    p.sendBlockChange(loc, Material.PORTAL, loc.getWorld().getBlockAt(loc).getData());;
  }
  
  public static void removeNetherPortalBlock(Player p, Location loc)
  {
    p.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getType(), loc.getWorld().getBlockAt(loc).getData());
  }
  
  public static void sendWallBlock(Player p, Location loc)
  {
    p.sendBlockChange(loc, Material.STAINED_GLASS, DyeColor.RED.getData());
  }
  
  public static void removeWallBlock(Player p, Location loc)
  {
    p.sendBlockChange(loc, loc.getWorld().getBlockAt(loc).getType(), loc.getWorld().getBlockAt(loc).getData());
  }
  
  public Player getPlayer()
  {
    return this.p;
  }
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public Location getFreeze()
  {
    return this.freeze;
  }
  
  public void setCancelled(boolean cancelled)
  {
    this.cancelled = cancelled;
    Player p;
    if ((cancelled) && 
      (Bukkit.getPlayer(this.name) != null))
    {
      p = Bukkit.getPlayer(this.name);
      for (Location loc : getWallBlocks(this.freeze)) {
        removeWallBlock(p, loc);
      }
      for (Location loc : getNetherPortalBlocks(this.freeze)) {
    	  removeNetherPortalBlock(p, loc);
      }
    }
  }
  
  @EventHandler
  public void onMove(PlayerMoveEvent e)
  {
    if (this.cancelled)
    {
      e.getHandlers().unregister(this);
      return;
    }
    if (Bukkit.getPlayer(this.name) != null)
    {
      Player p = Bukkit.getPlayer(this.name);
      if (e.getPlayer().getName().equals(p.getName()))
      {
        for (Location loc : getWallBlocks(this.freeze)) {
          sendWallBlock(e.getPlayer(), loc);
        }
        for (Location loc : getNetherPortalBlocks(this.freeze)) {
        	sendNetherPortalBlock(e.getPlayer(), loc);
        }
        if(Main.MessageOnMove) {
        p.sendMessage(ChatColor.RED + "You have been frozen, you have " + ChatColor.DARK_RED + Main.minutes + " minutes " + ChatColor.RED + "to join " + ChatColor.DARK_RED + Main.teamspeak + ChatColor.RED + ".");
        }
        if ((e.getTo().getBlockX() == e.getFrom().getBlockX()) && (e.getTo().getBlockY() == e.getFrom().getBlockY()) && (e.getTo().getBlockZ() == e.getFrom().getBlockZ())) {
          return;
        }
        e.setTo(this.freeze);
      }
    }
  }
  
  @EventHandler(priority=EventPriority.MONITOR)
  public void onJoin(PlayerJoinEvent e)
  {
    if (this.cancelled)
    {
      if (Bukkit.getPlayer(this.name) != null)
      {
        Player p = Bukkit.getPlayer(this.name);
        this.p = p;
        if (e.getPlayer().getName().equals(p.getName()))
        {
          e.getPlayer().setAllowFlight(false);
          for (Location loc : getWallBlocks(this.freeze)) {
            removeWallBlock(e.getPlayer(), loc);
          }
          for (Location loc : getNetherPortalBlocks(this.freeze)) {
        	  removeNetherPortalBlock(e.getPlayer(), loc);
          }
          e.getHandlers().unregister(this);
        }
      }
    }
    else if (Bukkit.getPlayer(this.name) != null)
    {
      Player p = Bukkit.getPlayer(this.name);
      if (e.getPlayer().getName().equals(p.getName()))
      {
        e.getPlayer().teleport(this.freeze);
        for (Location loc : getWallBlocks(this.freeze)) {
          sendWallBlock(e.getPlayer(), loc);
        }
        for (Location loc : getNetherPortalBlocks(this.freeze)) {
        	sendNetherPortalBlock(e.getPlayer(), loc);
        }
      }
    }
  }
  
  @EventHandler
  public void onDamage(EntityDamageEvent e)
  {
    if (this.cancelled)
    {
      e.getHandlers().unregister(this);
      return;
    }
    if ((e.getEntity() instanceof Player))
    {
      Player p = (Player)e.getEntity();
      if (p.getName().equalsIgnoreCase(this.name))
      {
        e.setDamage(0.0D);
        e.setCancelled(true);
      }
    }
  }
}
