package com.gmail.ZiomuuSs.Effects;

import org.bukkit.entity.Player;

public abstract class Effect {
  protected int value;
  protected String instruction;
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
      player.sendMessage(message);
    }
  }
  
  public boolean isStop () {
    return stop;
  }
  
  public boolean isCorrect () {
    return false;
  }
}
