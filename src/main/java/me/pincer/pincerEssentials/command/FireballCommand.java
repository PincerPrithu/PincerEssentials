package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class FireballCommand implements CommandExecutor {
    private final LanguageManager lang;

    public FireballCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.fireball")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        Fireball fb = player.launchProjectile(Fireball.class);
        fb.setVelocity(player.getLocation().getDirection().multiply(2.0));
        fb.setIsIncendiary(false);
        fb.setYield(2f);

        player.sendMessage(lang.getMessage("fireball_launch"));
        return true;
    }
}
