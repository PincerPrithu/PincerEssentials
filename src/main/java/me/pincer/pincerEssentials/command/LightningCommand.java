package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LightningCommand implements CommandExecutor {
    private final LanguageManager lang;

    public LightningCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.lightning")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        Player target;
        if (args.length == 0 && sender instanceof Player) {
            target = (Player) sender;
        } else if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(lang.getMessage("player_not_found"));
                return true;
            }
        } else {
            sender.sendMessage("&cUsage: /lightning [player]");
            return true;
        }

        Location loc = target.getLocation();
        loc.getWorld().strikeLightning(loc);
        sender.sendMessage(lang.getMessage("lightning_struck"));
        return true;
    }
}
