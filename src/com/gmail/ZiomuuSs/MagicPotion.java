package com.gmail.ZiomuuSs;

import java.util.ArrayList;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.ZiomuuSs.Effects.Effect;

public class MagicPotion implements Listener{
  public Main plugin;
  private ItemStack item;
  private String permission = null;
  private ArrayList<Effect> effects;

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
        for (Effect effect: effects) {
         effect.execute(player);
         if (effect.isStop())
           break;
        }
        player.getInventory().removeItem(item);
        e.setCancelled(true);
      }
    }
  }

  public void setEffects (ArrayList<Effect> effects) {
    this.effects = effects;
  }

  public void setPermission (String permission) {
    this.permission = permission;
  }

  public ItemStack getItem() {
    return this.item;
  }
}
