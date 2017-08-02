package com.gmail.ZiomuuSs;

import com.gmail.ZiomuuSs.Commands.MagicPotions;
import com.gmail.ZiomuuSs.Utils.ConfigAccessor;
import com.gmail.ZiomuuSs.Utils.LoadPotions;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin{
  public Main() {}
  public FileConfiguration potions;
  public FileConfiguration messages;
  public PluginManager pm = getServer().getPluginManager();
  private LoadPotions lp;
  private MagicPotions cmd;
  private ConfigAccessor potionsAccessor;
  private ConfigAccessor messagesAccessor;

  public void onEnable() {
    potionsAccessor = new ConfigAccessor(this, "potions.yml");
    messagesAccessor = new ConfigAccessor(this, "messages.yml");
    potionsAccessor.saveDefaultConfig();
    messagesAccessor.saveDefaultConfig();
    saveDefaultConfig();
    potions = potionsAccessor.getConfig();
    messages = messagesAccessor.getConfig();
    getConfig();
    lp = new LoadPotions(this);
    cmd = new MagicPotions(this, lp);
    getCommand("magicpotions").setExecutor(cmd);
  }

  public void onDisable() {
  }

  public int[] reload() {
    PlayerItemConsumeEvent.getHandlerList().unregister(this);
    lp.deletePotions();
    lp = null;
    potionsAccessor.reloadConfig();
    messagesAccessor.reloadConfig();
    reloadConfig();
    potionsAccessor.saveDefaultConfig();
    messagesAccessor.saveDefaultConfig();
    saveDefaultConfig();
    potions = potionsAccessor.getConfig();
    messages = messagesAccessor.getConfig();
    getConfig();
    lp = new LoadPotions(this);
    cmd.updatePotions(lp);
    return new int[] {lp.loaded, lp.total};
  }

}
