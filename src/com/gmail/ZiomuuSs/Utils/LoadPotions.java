package com.gmail.ZiomuuSs.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffectType;

import com.gmail.ZiomuuSs.MagicPotion;
import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Effects.Effect;
import com.gmail.ZiomuuSs.Effects.HealthEffect;
import com.gmail.ZiomuuSs.Effects.ManaEffect;
import com.gmail.ZiomuuSs.Effects.PotionEffects;

public class LoadPotions {
  public Main plugin;
  private static final Logger LOG = Bukkit.getLogger();
  private static ThrowError Error = new ThrowError();
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
      ItemStack it = createItem(this.potions.getString("potions."+potion+".name"), (List<String>) this.potions.getList("potions."+potion+".lore"), this.potions.getInt("potions."+potion+".id"), this.potions.getInt("potions."+potion+".data"));
      if (it.getType().isEdible() || it.getType().equals(Material.POTION)) {
        MagicPotions.put(potion, new MagicPotion(this.plugin, it));
        MagicPotions.get(potion).setEffects(parseEffects(potion));
        if (this.potions.isConfigurationSection("potions."+potion+".permission")) {
          MagicPotions.get(potion).setPermission(this.potions.getString("potions."+potion+".permission"));
        }
        
        i++;
      } else {
        Error.show("Invalid Material", potion);
      }
    }
    loaded = i;
    LOG.info("Loaded "+loaded+"/"+total+" potions!");
  }

  private ItemStack createItem(String name, List<String> lore, int ID, int data) {
    ItemStack item = new ItemStack(Material.getMaterial(ID), 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    for (int i = 0; i < lore.size(); i++) {
      lore.set(i, ChatColor.translateAlternateColorCodes('&', lore.get(i)));
    }
    meta.setLore(lore);
    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
    item.setItemMeta(meta);
    MaterialData mt = item.getData();
    mt.setData((byte) data);
    item.setData(mt);
    return item;
  }

  private ArrayList<Effect> parseEffects(String potion) {
    String path = "potions."+potion+".effects";
    ArrayList<Effect> effects = new ArrayList<Effect>();
    Effect eff = null;
    for (String effect : potions.getConfigurationSection(path).getKeys(false)) {
      switch (effect.toLowerCase()) {
      case "mana":
        if (potions.isInt(path+"."+effect+".value")) {
          eff = new ManaEffect(potions.getInt(path+"."+effect+".value"));
        } else {
          Error.show("Missing required", potion, effect, "value");
          break;
        }
        eff = parseAdditionalArguments(eff, path+"."+effect);
        effects.add(eff);
        break;
      case "health":
        if (potions.isInt(path+"."+effect+".value")) {
          eff = new HealthEffect(potions.getInt(path+"."+effect+".value"));
        }
        eff = parseAdditionalArguments(eff, path+"."+effect);
        effects.add(eff);
        break;
      case "potion":
        if (potions.isInt(path+"."+effect+".value")) {
          if (potions.isString(path+"."+effect+".type")) {
            if (PotionEffectType.getByName(potions.getString(path+"."+effect+".type").toUpperCase()) != null) {
              if (potions.isInt(path+"."+effect+".time")) {
                eff = new PotionEffects(potions.getInt(path+"."+effect+".value"), potions.getString(path+"."+effect+".type"), potions.getInt(path+"."+effect+".time"));
                eff = parseAdditionalArguments(eff, path+"."+effect);
                effects.add(eff);
              break;
              } else {
                Error.show("Missing required", potion, effect, "time");
                break;
              }
            } else {
              Error.show("Invalid Potion", potion, effect, potions.getString(path+"."+effect+".type").toUpperCase());
              break;
            }
          } else {
            Error.show("Missing required", potion, effect, "type");
            break;
          }
        } else {
          Error.show("Missing required", potion, effect, "value");
          break;
        }
      default:
        Error.show("Invalid Effect", potion, effect);
        break;
      }
    }
    return effects;
  }

  private Effect parseAdditionalArguments (Effect effect, String path) {
    if (potions.isBoolean(path+".stop")) {
      effect.setStoppable(potions.getBoolean(path+".stop"));
    }
    if (potions.isInt(path+".chance")) {
      effect.setChance(potions.getInt(path+".chance"));
    }
    if (potions.isString(path+".message")) {
      effect.setMessage(potions.getString(path+".message"));
    }
    return effect;
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
