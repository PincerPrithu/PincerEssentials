package me.pincer.pincerEssentials;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Holds ephemeral states in memory:
 *  - vanishPlayers: who is vanished
 *  - tpaDisabled: which players have TPA toggled off
 */
public class StateManager {
    private final Set<UUID> vanishPlayers = ConcurrentHashMap.newKeySet();
    private final ConcurrentMap<UUID, Boolean> tpaDisabled = new ConcurrentHashMap<>();

    // ======================
    // Vanish Methods
    // ======================
    public boolean isVanished(Player player) {
        return vanishPlayers.contains(player.getUniqueId());
    }

    public void setVanished(Player player, boolean vanish) {
        if (vanish) vanishPlayers.add(player.getUniqueId());
        else vanishPlayers.remove(player.getUniqueId());
    }

    // ======================
    // TPToggle (TPA Disabled) Methods
    // ======================
    public boolean isTpaDisabled(Player player) {
        return tpaDisabled.getOrDefault(player.getUniqueId(), false);
    }

    public void setTpaDisabled(Player player, boolean disabled) {
        tpaDisabled.put(player.getUniqueId(), disabled);
    }
}
