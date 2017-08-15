package com.gmail.ZiomuuSs.Effects;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Effect {
  protected int value;
  protected int chance = 100;
  protected boolean stop = false;
  protected String message = null;
  
  public void setChance (int chance) {
    this.chance = chance;
  }
  
  public void setStoppable (boolean stop) {
    this.stop = stop;
  }
  
  public void setMessage (String message) {
    this.message = message;
  }
  
  public void execute (Player player) {
    if (message != null) {
      player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
  }
  
  public boolean isStop () {
    return stop;
  }
  
}
