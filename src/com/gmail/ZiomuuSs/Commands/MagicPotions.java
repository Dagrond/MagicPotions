package com.gmail.ZiomuuSs.Commands;

import com.gmail.ZiomuuSs.Main;
import com.gmail.ZiomuuSs.Utils.LoadPotions;
import com.gmail.ZiomuuSs.Utils.Messages;
import java.util.ArrayList;
import static org.bukkit.Bukkit.getServer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MagicPotions implements CommandExecutor{
  Main plugin;
  private LoadPotions lp;
  private Messages msg;
  private ArrayList<String> LoadedPotions;
  public MagicPotions(Main instance, LoadPotions lp) {
    plugin = instance;
    this.lp = lp;
    msg = new Messages(plugin);
    LoadedPotions = new ArrayList<String>(lp.MagicPotions.keySet());
  }
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player pl = (Player) sender;
    if (cmd.getName().equalsIgnoreCase("MagicPotions") || cmd.getName().equalsIgnoreCase("mp")) {
      if (args[0].equalsIgnoreCase("get") || args[0].equalsIgnoreCase("give")) {
        if (!(args.length < 2)) {
          if (args.length < 3) {
            if (pl.hasPermission("magicpotions.*") || pl.hasPermission("magicpotions.give")) {
              if (LoadedPotions.contains(args[1])) {
                pl.getInventory().addItem(lp.getPotion(args[1]).getItem());
                pl.sendMessage(msg.getMessage("potion-added", true, args[1]));
                return true;
              } else {
                pl.sendMessage(msg.getMessage("potion-not-exist", true, args[1]));
                return true;
              }
            } else {
              pl.sendMessage(msg.getMessage("no-permissions", true));
              return true;
            }
          } else {
            if (pl.hasPermission("magicpotions.*") || pl.hasPermission("magicpotions.give.others")) {
              if (LoadedPotions.contains(args[1])) {
                Player player = getServer().getPlayer(args[2]);
                if (player.isOnline()) {
                  player.getInventory().addItem(lp.getPotion(args[1]).getItem());
                  pl.sendMessage(msg.getMessage("potion-added-other", true, args[1], args[2]));
                  return true;
                } else {
                  pl.sendMessage(msg.getMessage("player-offline", true, args[2]));
                  return true;
                }
              } else {
                pl.sendMessage(msg.getMessage("potion-not-exist", true, args[1]));
                return true;
              }
            } else {
              pl.sendMessage(msg.getMessage("no-permissions", true));
              return true;
            }
          }
        } else {
          sender.sendMessage(msg.getMessage("usage-get-potion", true));
          return true;
        }
      } else if (args[0].equalsIgnoreCase("reload")) {
        pl.sendMessage(msg.getMessage("reloading", true));
        int[] i = plugin.reload();
        if (i[0] == i[1]) {
          pl.sendMessage(msg.getMessage("succ-reloaded", true, Integer.toString(i[0])));
        } else {
          pl.sendMessage(msg.getMessage("fail-reloaded", true, Integer.toString(i[0]), Integer.toString(i[1])));
        }
        return true;
      } else if (args[0].equalsIgnoreCase("list")) {
        if (pl.hasPermission("magicpotions.*") || pl.hasPermission("magicpotions.list")) {
          pl.sendMessage(msg.getMessage("loaded", true)+LoadedPotions);
          return true;
        }
      }
    }
    return false;
  }
  public void updatePotions(LoadPotions lp) {
    this.lp = lp;
    LoadedPotions = new ArrayList<String>(lp.MagicPotions.keySet());
  }
}