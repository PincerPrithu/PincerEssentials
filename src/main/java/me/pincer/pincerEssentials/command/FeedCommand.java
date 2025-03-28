package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class FeedCommand implements CommandExecutor {
    private final LanguageManager lang;

    public FeedCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.feed")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        if (args.length == 0) {
            // /feed self
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.getMessage("command_player_only"));
                return true;
            }
            Player p = (Player) sender;
            p.setFoodLevel(20);
            p.setSaturation(20f);
            p.sendMessage(lang.getMessage("feed_self"));
        } else {
            // /feed <player>
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(lang.getMessage("player_not_found"));
                return true;
            }
            target.setFoodLevel(20);
            target.setSaturation(20f);
            sender.sendMessage(lang.getMessage("feed_other").replace("{player}", target.getName()));
            target.sendMessage(lang.getMessage("feed_self"));
        }
        return true;
    }
}
