package com.gmail.ZiomuuSs.Effects;

import org.bukkit.entity.Player;


public class HealthEffect extends Effect {
  
  public HealthEffect (int value) {
    this.value = value;
  }

  @Override
  public void execute (Player player) {
    if ((Math.random() * 100) <= chance) {
      super.execute(player);
      if (player.getMaxHealth() < player.getHealth()+value) {
        player.setHealth(player.getMaxHealth());
      } else {
        player.setHealth(player.getHealth()+value);
      }
    }
  }
}
