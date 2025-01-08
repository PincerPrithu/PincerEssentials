// Java
package me.pincer.pincerEssentials.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.Map;
import java.util.UUID;

public class OpenInventoryListener implements Listener {
    private final Map<UUID, Integer> taskMap;

    public OpenInventoryListener(Map<UUID, Integer> taskMap) {
        this.taskMap = taskMap;
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