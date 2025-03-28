package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;


public class JumpCommand implements CommandExecutor {
    private final LanguageManager lang;

    public JumpCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("essentials.jump")) {
            p.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        BlockIterator it = new BlockIterator(p, 100);
        Block last = it.next();
        while (it.hasNext()) {
            last = it.next();
            if (last.getType() != Material.AIR) {
                break;
            }
        }
        p.teleport(last.getLocation().add(0, 1, 0));
        p.sendMessage(lang.getMessage("jump_success"));
        return true;
    }
}
