package com.gmail.ZiomuuSs.Effects;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffects extends Effect {
  private PotionEffectType type;
  private int time;

  public PotionEffects (int value, String type, int time) {
    this.value = value;
    this.type = PotionEffectType.getByName(type.toUpperCase());
    this.time = time;
  }

  @Override
  public void execute (Player player) {
    if ((Math.random() * 100) <= chance) {
      super.execute(player);
      if (player.hasPotionEffect(type)) {
        if (player.getPotionEffect(type).getAmplifier() < value) {
          player.removePotionEffect(type);
          player.addPotionEffect(new PotionEffect(type, time*20, value-1));
        } else if (player.getPotionEffect(type).getAmplifier() == value){
          int temp = (player.getPotionEffect(type).getDuration())+(value*20);
          player.removePotionEffect(type);
          player.addPotionEffect(new PotionEffect(type, temp, value-1));
        } else {
         return; 
        }
      } else {
        player.addPotionEffect(new PotionEffect(type, time*20, value-1));
      }
    }
  }
}
