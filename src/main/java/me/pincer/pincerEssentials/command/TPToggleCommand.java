package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import me.pincer.pincerEssentials.managers.TeleportManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TPToggleCommand implements CommandExecutor {
    private final LanguageManager lang;
    private final TeleportManager teleportManager;

    public TPToggleCommand(LanguageManager lang, TeleportManager tpManager) {
        this.lang = lang;
        this.teleportManager = tpManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("essentials.tptoggle")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        // We can just call the manager's setTpaDisabled:
        boolean currentlyDisabled = teleportManager
                .isTpaDisabled(player); // We'll add that method for convenience
        teleportManager.setTpaDisabled(player, !currentlyDisabled);

        if (!currentlyDisabled) {
            player.sendMessage(lang.getMessage("tptoggle_on"));
        } else {
            player.sendMessage(lang.getMessage("tptoggle_off"));
        }
        return true;
    }

}
