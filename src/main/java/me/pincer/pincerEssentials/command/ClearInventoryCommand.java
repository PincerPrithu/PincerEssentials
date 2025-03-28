package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClearInventoryCommand implements CommandExecutor {
    private final LanguageManager lang;

    public ClearInventoryCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.clearinventory")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        if (args.length == 0) {
            // Clear self
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.getMessage("command_player_only"));
                return true;
            }
            Player p = (Player) sender;
            p.getInventory().clear();
            p.sendMessage(lang.getMessage("clearinventory_self"));
        } else {
            // Clear target
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(lang.getMessage("player_not_found"));
                return true;
            }
            target.getInventory().clear();
            sender.sendMessage(lang.getMessage("clearinventory_other").replace("{player}", target.getName()));
            target.sendMessage(lang.getMessage("clearinventory_self"));
        }
        return true;
    }
}
