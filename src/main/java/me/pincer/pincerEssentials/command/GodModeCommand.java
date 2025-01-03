package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GodModeCommand implements CommandExecutor {
    private final LanguageManager languageManager;

    public GodModeCommand() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;

        if (p.isInvulnerable()) {
            p.setInvulnerable(false);
            p.sendMessage(languageManager.getMessage("god_mode_disabled"));
            return true;
        }

        p.setInvulnerable(true);
        p.sendMessage(languageManager.getMessage("god_mode_enabled"));

        return true;
    }
}