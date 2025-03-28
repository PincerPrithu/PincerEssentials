package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class KillCommand implements CommandExecutor {
    private final LanguageManager lang;

    public KillCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.kill")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(lang.getMessage("kill_usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(lang.getMessage("player_not_found"));
            return true;
        }

        target.setHealth(0);
        sender.sendMessage(lang.getMessage("kill_player").replace("{player}", target.getName()));
        if (target.isOnline()) {
            target.sendMessage(lang.getMessage("killed_by_admin").replace("{player}", sender.getName()));
        }
        return true;
    }
}
