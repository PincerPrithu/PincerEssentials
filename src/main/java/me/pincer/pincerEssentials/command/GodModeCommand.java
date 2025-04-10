package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class GodModeCommand implements CommandExecutor {
    private final LanguageManager lang;

    public GodModeCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;

        if (!player.hasPermission("essentials.godmode")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        boolean wasInvulnerable = player.isInvulnerable();
        player.setInvulnerable(!wasInvulnerable);

        if (!wasInvulnerable) player.sendMessage(lang.getMessage("god_mode_enabled"));
        else player.sendMessage(lang.getMessage("god_mode_disabled"));

        return true;
    }
}
