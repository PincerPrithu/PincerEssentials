package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OpenEnderCommand implements CommandExecutor {
    private final Map<UUID, Integer> taskMap = new HashMap<>();
    private final LanguageManager lang;

    public OpenEnderCommand(LanguageManager lang) {
        this.lang = lang;
    }

    public Map<UUID, Integer> getTaskMap() {
        return taskMap;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player opener = (Player) sender;

        if (!opener.hasPermission("essentials.openender")) {
            opener.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        if (args.length == 0) {
            opener.sendMessage("Usage: /openender <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            opener.sendMessage(lang.getMessage("tpa_player_not_found"));
            return true;
        }

        Inventory enderInv = target.getEnderChest();
        opener.openInventory(enderInv);

        int taskId = new BukkitRunnable() {
            @Override
            public void run() {
                opener.updateInventory();
            }
        }.runTaskTimer(PincerEssentials.getInstance(), 0L, 10L).getTaskId();

        taskMap.put(opener.getUniqueId(), taskId);
        return true;
    }
}
