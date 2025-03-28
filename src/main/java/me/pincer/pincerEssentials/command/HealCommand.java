package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class HealCommand implements CommandExecutor {
    private final LanguageManager lang;

    public HealCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.heal")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        if (args.length == 0) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(lang.getMessage("command_player_only"));
                return true;
            }
            Player p = (Player) sender;
            p.setHealth(p.getMaxHealth());
            p.setFoodLevel(20);
            p.setSaturation(20f);
            p.sendMessage(lang.getMessage("heal_self"));
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(lang.getMessage("player_not_found"));
                return true;
            }
            target.setHealth(target.getMaxHealth());
            target.setFoodLevel(20);
            target.setSaturation(20f);

            sender.sendMessage(lang.getMessage("heal_other").replace("{player}", target.getName()));
            target.sendMessage(lang.getMessage("heal_self"));
        }
        return true;
    }
}
