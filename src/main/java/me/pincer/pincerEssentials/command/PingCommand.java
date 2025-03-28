package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class PingCommand implements CommandExecutor {
    private final LanguageManager lang;

    public PingCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.ping")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        sender.sendMessage(lang.getMessage("ping_pong"));
        return true;
    }
}
