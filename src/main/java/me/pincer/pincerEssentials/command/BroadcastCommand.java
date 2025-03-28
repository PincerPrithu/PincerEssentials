package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class BroadcastCommand implements CommandExecutor {
    private final LanguageManager lang;

    public BroadcastCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.broadcast")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(lang.getMessage("broadcast_usage"));
            return true;
        }

        String msg = String.join(" ", args);
        String prefix = lang.getMessage("broadcast_prefix");
        Bukkit.broadcastMessage(prefix + msg);
        return true;
    }
}
