package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import me.pincer.pincerEssentials.StateManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class VanishCommand implements CommandExecutor {
    private final LanguageManager lang;
    private final StateManager stateManager;

    public VanishCommand(LanguageManager lang, StateManager stateManager) {
        this.lang = lang;
        this.stateManager = stateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.vanish")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        boolean wasVanished = stateManager.isVanished(player);
        stateManager.setVanished(player, !wasVanished);

        if (!wasVanished) {
            // we are vanishing
            for (Player online : Bukkit.getOnlinePlayers()) {
                if (!online.hasPermission("essentials.vanish.see")) {
                    online.hidePlayer(player);
                }
            }
            player.sendMessage(lang.getMessage("vanish_enabled"));
        } else {
            // we are unvanishing
            for (Player online : Bukkit.getOnlinePlayers()) {
                online.showPlayer(player);
            }
            player.sendMessage(lang.getMessage("vanish_disabled"));
        }
        return true;
    }
}
