package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class SpeedCommand implements CommandExecutor {
    private final LanguageManager lang;

    public SpeedCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.speed")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        // /speed [walk|fly] <speed> [player]
        if (args.length < 1 || args.length > 3) {
            sender.sendMessage(lang.getMessage("speed_usage"));
            return true;
        }

        String type;
        float value;
        Player target;

        // Figure out if first arg is 'walk'/'fly' or speed value
        if ("walk".equalsIgnoreCase(args[0]) || "fly".equalsIgnoreCase(args[0])) {
            // type is first arg
            type = args[0].toLowerCase();
            // second arg must be speed
            if (args.length < 2) {
                sender.sendMessage(lang.getMessage("speed_usage"));
                return true;
            }
            try {
                value = Float.parseFloat(args[1]);
            } catch (NumberFormatException e) {
                sender.sendMessage(lang.getMessage("speed_invalid"));
                return true;
            }
            // third arg if present is target player
            if (args.length == 3) {
                target = Bukkit.getPlayer(args[2]);
                if (target == null) {
                    sender.sendMessage(lang.getMessage("player_not_found"));
                    return true;
                }
            } else {
                // if console, we must specify a target
                if (!(sender instanceof Player)) {
                    sender.sendMessage(lang.getMessage("speed_usage"));
                    return true;
                }
                target = (Player) sender;
            }
        } else {
            // first arg might be speed, default type to walk
            try {
                value = Float.parseFloat(args[0]);
            } catch (NumberFormatException ex) {
                sender.sendMessage(lang.getMessage("speed_invalid"));
                return true;
            }
            type = "walk";
            // if second arg is present, it could be target
            if (args.length == 2) {
                target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(lang.getMessage("player_not_found"));
                    return true;
                }
            } else {
                // console check
                if (!(sender instanceof Player)) {
                    sender.sendMessage(lang.getMessage("speed_usage"));
                    return true;
                }
                target = (Player) sender;
            }
        }

        // Validate speed
        if (value < 0 || value > 10) {
            sender.sendMessage(lang.getMessage("speed_invalid"));
            return true;
        }
        float mcSpeed = value / 10.0f;

        if ("fly".equals(type)) {
            target.setFlySpeed(mcSpeed);
        } else {
            target.setWalkSpeed(mcSpeed);
        }

        if (target.equals(sender)) {
            sender.sendMessage(lang.getMessage("speed_set_self")
                    .replace("{type}", type)
                    .replace("{speed}", String.valueOf(value)));
        } else {
            sender.sendMessage(lang.getMessage("speed_set_other")
                    .replace("{player}", target.getName())
                    .replace("{type}", type)
                    .replace("{speed}", String.valueOf(value)));
        }
        return true;
    }
}
