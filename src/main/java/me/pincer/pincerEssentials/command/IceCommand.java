package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class IceCommand implements CommandExecutor {
    private final LanguageManager lang;

    public IceCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.ice")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        if (args.length < 1) {
            sender.sendMessage(lang.getMessage("ice_usage"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(lang.getMessage("player_not_found"));
            return true;
        }

        // Example: freeze by setting walk speed to 0
        target.setWalkSpeed(0.0f);

        sender.sendMessage(lang.getMessage("ice_success").replace("{player}", target.getName()));
        return true;
    }
}
