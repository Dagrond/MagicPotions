package com.gmail.ZiomuuSs.Utils;

import com.gmail.ZiomuuSs.MagicPotion;
import com.gmail.ZiomuuSs.Main;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class LoadPotions {
  public Main plugin;
  private final List<String> EFFECT = Arrays.asList("mana", "potion");
  private static final Logger LOG = Bukkit.getLogger();
  private FileConfiguration potions;
  public HashMap<String, MagicPotion> MagicPotions = new HashMap<String, MagicPotion>();
  public int total;
  public int loaded;

  public LoadPotions(Main instance) {
    plugin = instance;
    potions = plugin.potions;
    LOG.info("Loading Potions...");
    List<String> potions = new ArrayList<>();
    potions.addAll(this.potions.getConfigurationSection("potions").getKeys(false));
    total = potions.size();
    int i = 0;
    for (String potion: potions) {
      ItemStack it = createItem(this.potions.getString("potions."+potion+".name"), this.potions.getList("potions."+potion+".lore"), this.potions.getInt("potions."+potion+".id"), this.potions.getInt("potions."+potion+".data"));
      if (it.getType().isEdible() || it.getType().equals(Material.POTION)) {
        MagicPotions.put(potion, new MagicPotion(this.plugin, it));
        MagicPotions.get(potion).setEffects(parseEffects(potion));
        if (this.potions.isConfigurationSection("potions."+potion+".permission")) {
          MagicPotions.get(potion).setPermission(this.potions.getString("potions."+potion+".permission"));
        }
        
        i++;
      } else {
        throwError("Invalid Material", potion, null);
      }
    }
    loaded = i;
    LOG.info("Loaded "+loaded+"/"+total+" potions!");
  }

  private ItemStack createItem(String name, List lore, int ID, int data) {
    ItemStack item = new ItemStack(Material.getMaterial(ID));
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    meta.setLore(lore);
    item.setItemMeta(meta);
    MaterialData mt = item.getData();
    mt.setData((byte) data);
    item.setData(mt);
    return item;
  }

  private ArrayList<Object> parseEffects(String path) {
    path = "potions."+path+".effects";
    ArrayList<Object> effects = new ArrayList<Object>();
    for (String effect : potions.getConfigurationSection(path).getKeys(false)) {
      try {
        effects.add(Class.forName(effect).newInstance());
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
        throwError ("Invalid Effect", path, effect);
      }
    }
    return effects;
  }

  private void throwError (String cause, String potion, String additional) {
    LOG.log(Level.SEVERE, "Error loading "+potion+": ");
    if (cause.equalsIgnoreCase("Invalid Material")) {
      LOG.log(Level.SEVERE, "Material for potion must be consumeable!");
    } else if (cause.equalsIgnoreCase("Invalid Effect")) {
      LOG.log(Level.SEVERE, "There is no effect called "+additional+"!");
    }
  }

  public MagicPotion getPotion (String name) {
    return MagicPotions.get(name);
  }

  public void deletePotions () {
    for (String key: this.MagicPotions.keySet()) {
      MagicPotions.put(key, null);
    }
  }

}
