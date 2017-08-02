package com.gmail.ZiomuuSs.Utils;

import com.gmail.ZiomuuSs.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Messages {
  private FileConfiguration messages;
  private Main plugin;

  public Messages (Main instance) {
    plugin = instance;
    messages = plugin.messages;
  }

  public String getMessage(String path, boolean prefix, String ... opt) {
    String msg = messages.getString(path);
    if (prefix) {
      msg = messages.getString("prefix")+" "+msg;
    }
    try {
      msg = msg.replaceAll("%x", opt[0]);
    } catch (ArrayIndexOutOfBoundsException e) {}
    try {
      msg = msg.replaceAll("%y", opt[1]);
    } catch (ArrayIndexOutOfBoundsException e) {}
    try {
      msg = msg.replaceAll("%z", opt[2]);
    } catch (ArrayIndexOutOfBoundsException e) {}
    msg = ChatColor.translateAlternateColorCodes('&', msg);
    return msg;
  }
}
