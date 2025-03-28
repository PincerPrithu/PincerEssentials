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
        if (!(event.getPlayer() instanceof Player)) return;
        Player player = (Player) event.getPlayer();
        UUID id = player.getUniqueId();

        if (taskMap.containsKey(id)) {
            Bukkit.getScheduler().cancelTask(taskMap.get(id));
            taskMap.remove(id);
        }
    }
}
