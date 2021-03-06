package com.gmail.ZiomuuSs.Utils;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Bukkit;

public final class ThrowError {
  
  private static final Logger LOG = Bukkit.getLogger();
  
  public void show (String cause, String potion, String...additional) {
    LOG.log(Level.SEVERE, "[MagicPotions] Error loading "+potion+": ");
    if (cause.equalsIgnoreCase("Invalid Material")) {
      LOG.log(Level.SEVERE, "[MagicPotions] Material for potion must be consumeable!");
    } else if (cause.equalsIgnoreCase("Invalid Effect")) {
      LOG.log(Level.SEVERE, "[MagicPotions] There is no effect called "+additional[0]+"!");
    } else if (cause.equalsIgnoreCase("Missing required")) {
      LOG.log(Level.SEVERE, "[MagicPotions] Cannot parse effect "+additional[0]+":");
      LOG.log(Level.SEVERE, "[MagicPotions] Missing required section: "+additional[1]+"!");
    } else if (cause.equalsIgnoreCase("Invalid potion")) {
      LOG.log(Level.SEVERE, "[MagicPotions] Cannot parse effect "+additional[0]+":");
      LOG.log(Level.SEVERE, "[MagicPotions] Invalid potion effect: "+additional[1]+"!");
    }
  }
}
