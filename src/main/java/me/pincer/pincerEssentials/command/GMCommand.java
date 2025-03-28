package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GMCommand implements CommandExecutor {
    private final LanguageManager lang;

    public GMCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("gamemode." + label.toLowerCase())) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        switch (label.toLowerCase()) {
            case "gms":
                player.setGameMode(GameMode.SURVIVAL);
                player.sendMessage(lang.getMessage("gamemode_survival"));
                break;
            case "gmc":
                player.setGameMode(GameMode.CREATIVE);
                player.sendMessage(lang.getMessage("gamemode_creative"));
                break;
            case "gmsp":
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage(lang.getMessage("gamemode_spectator"));
                break;
            case "gma":
                player.setGameMode(GameMode.ADVENTURE);
                player.sendMessage(lang.getMessage("gamemode_adventure"));
                break;
        }
        return true;
    }
}
