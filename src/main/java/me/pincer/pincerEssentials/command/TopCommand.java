package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class TopCommand implements CommandExecutor {
    private final LanguageManager lang;

    public TopCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player p = (Player) sender;
        if (!p.hasPermission("essentials.top")) {
            p.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        World world = p.getWorld();
        Location loc = p.getLocation();
        int highestY = world.getHighestBlockYAt(loc);
        Location top = new Location(world, loc.getX(), highestY + 1, loc.getZ(), loc.getYaw(), loc.getPitch());

        // If there's a block in the new location, go one higher
        if (top.getBlock().getType() != Material.AIR) {
            top.add(0, 1, 0);
        }
        p.teleport(top);
        p.sendMessage(lang.getMessage("top_success"));
        return true;
    }
}
