package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class FlyCommand implements CommandExecutor {
    private final LanguageManager lang;

    public FlyCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.fly")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        Player target;
        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.getMessage("command_player_only"));
                return true;
            }
            target = (Player) sender;
        } else {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(lang.getMessage("player_not_found"));
                return true;
            }
        }

        boolean newState = !target.getAllowFlight();
        target.setAllowFlight(newState);
        target.setFlying(newState);

        // Self or other
        if (sender.equals(target)) {
            sender.sendMessage(newState
                    ? lang.getMessage("fly_enabled_self")
                    : lang.getMessage("fly_disabled_self"));
        } else {
            sender.sendMessage(newState
                    ? lang.getMessage("fly_enabled_other").replace("{player}", target.getName())
                    : lang.getMessage("fly_disabled_other").replace("{player}", target.getName()));
            // also tell target
            target.sendMessage(newState
                    ? lang.getMessage("fly_enabled_self")
                    : lang.getMessage("fly_disabled_self"));
        }
        return true;
    }
}
