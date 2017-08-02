package com.gmail.ZiomuuSs.Effects;

import org.bukkit.entity.Player;

public abstract class Effect {
  protected int value;
  protected String instruction;
  protected int chance;
  protected boolean stop;
  
  public Effect (String instruction, int value, int chance, boolean stop) {
    this.instruction = instruction;
    this.value = value;
    this.chance = chance;
    this.stop = stop;
  }
  
  protected void execute (Player player) {}
  
}
