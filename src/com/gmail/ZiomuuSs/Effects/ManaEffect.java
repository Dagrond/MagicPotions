package com.gmail.ZiomuuSs.Effects;

import org.bukkit.entity.Player;

import com.sucy.skill.SkillAPI;

public class ManaEffect extends Effect{

  public ManaEffect (int value) {
    this.value = value;
  }

  @Override
  public void execute (Player player) {
    if ((Math.random() * 100) <= chance) {
      super.execute(player);
      SkillAPI.getPlayerData(player).setMana(SkillAPI.getPlayerData(player).getMana()+value);
    }
  }
}
