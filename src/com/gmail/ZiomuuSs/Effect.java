package com.gmail.ZiomuuSs;

import org.bukkit.entity.Player;

public class Effect {
  private boolean stop = false;
  private String instruction;
  private int value;
  
  public Effect(String instruction, int value) {
    this.instruction = instruction;
    this.value = value;
  }
  
  public Effect() {};
  
  public void execute (Player player) {}
}
