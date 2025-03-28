package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class DepthCommand implements CommandExecutor {
    private final LanguageManager lang;

    public DepthCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.depth")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        int y = player.getLocation().getBlockY();
        player.sendMessage(lang.getMessage("depth_info").replace("{y}", String.valueOf(y)));
        return true;
    }
}
