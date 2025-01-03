package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GMCommand implements CommandExecutor {
    private final LanguageManager languageManager;

    public GMCommand() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        if (!p.hasPermission("gamemode." + s)) {
            p.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
            return true;
        }
        switch (s) {
            case "gms":
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(ChatColor.GREEN + languageManager.getMessage("gamemode_survival"));
                break;
            case "gmc":
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(ChatColor.GREEN + languageManager.getMessage("gamemode_creative"));
                break;
            case "gmsp":
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(ChatColor.GREEN + languageManager.getMessage("gamemode_spectator"));
                break;
            case "gma":
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(ChatColor.GREEN + languageManager.getMessage("gamemode_adventure"));
                break;
        }
        return true;
    }
}