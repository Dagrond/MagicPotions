package com.gmail.ZiomuuSs;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

public class MagicPotion implements Listener{
  public Main plugin;
  private ItemStack item;
  private String permission = null;
  private ArrayList<Object> effects;

  public MagicPotion(Main instance, ItemStack item) {
    plugin = instance;
    this.item = item;
    plugin.pm.registerEvents(this, plugin);
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void ConsumeEvent(PlayerItemConsumeEvent e) {
    if (e.getItem().equals(this.item)) {
      Player player = e.getPlayer();
      if (this.permission == null || player.hasPermission(permission)) {
        player.sendMessage(effects+"");
      }
    }
  }

  public void setEffects (ArrayList<Object> effects) {
    this.effects = effects;
  }

  public void setPermission (String permission) {
    this.permission = permission;
  }

  public ItemStack getItem() {
    return this.item;
  }
}
