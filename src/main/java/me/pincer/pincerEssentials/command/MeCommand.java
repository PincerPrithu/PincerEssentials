package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class MeCommand implements CommandExecutor {
    private final LanguageManager lang;

    public MeCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.me")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage("&cUsage: /me <message...>");
            return true;
        }
        String message = String.join(" ", args);
        String format = lang.getMessage("me_format")
                .replace("{player}", sender.getName())
                .replace("{message}", message);
        Bukkit.broadcastMessage(format);
        return true;
    }
}
