package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OpenInventoryCommand implements CommandExecutor, Listener {
    private final Map<UUID, Integer> taskMap = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        if (args.length == 0) return false;

        Player t = Bukkit.getPlayer(args[0]);
        if (t != null) {
            Inventory targetInventory = t.getInventory();
            p.openInventory(targetInventory);

            int taskId = new BukkitRunnable() {
                @Override
                public void run() {
                    p.updateInventory();
                }
            }.runTaskTimer(PincerEssentials.getInstance(), 0L, 10L).getTaskId();

            taskMap.put(p.getUniqueId(), taskId);
        }
        return true;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        UUID playerId = player.getUniqueId();

        if (taskMap.containsKey(playerId)) {
            Bukkit.getScheduler().cancelTask(taskMap.get(playerId));
            taskMap.remove(playerId);
        }
    }
}