package me.pincer.pincerEssentials.managers;

import me.pincer.pincerEssentials.LanguageManager;
import me.pincer.pincerEssentials.StateManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TeleportManager implements CommandExecutor {

    private final LanguageManager languageManager;
    private final StateManager stateManager;

    private final Map<UUID, TeleportRequest> teleportRequests = new ConcurrentHashMap<>();
    private final Map<UUID, Location> backLocations = new ConcurrentHashMap<>();
    private final Map<UUID, Location> homeLocations = new ConcurrentHashMap<>();
    private final Map<String, Location> warps = new ConcurrentHashMap<>();

    private enum RequestType { TPA, TPAHERE; }

    private static class TeleportRequest {
        final UUID requesterId;
        final UUID targetId;
        final RequestType type;

        TeleportRequest(UUID requesterId, UUID targetId, RequestType type) {
            this.requesterId = requesterId;
            this.targetId = targetId;
            this.type = type;
        }
    }

    public TeleportManager(LanguageManager languageManager, StateManager stateManager) {
        this.languageManager = languageManager;
        this.stateManager = stateManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(languageManager.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        String cmd = command.getName().toLowerCase();

        switch (cmd) {
            case "tpa":
                return handleTpa(player, args);
            case "tpahere":
                return handleTpahere(player, args);
            case "tpaccept":
                return handleTpAccept(player);
            case "tpdeny":
                return handleTpDeny(player);
            case "tp":
                return handleTp(player, args);
            case "tphere":
                return handleTphere(player, args);
            case "back":
                return handleBack(player);
            case "sethome":
                return handleSetHome(player);
            case "home":
                return handleHome(player);
            case "setwarp":
                return handleSetWarp(player, args);
            case "warp":
                return handleWarp(player, args);
            case "delwarp":
                return handleDelWarp(player, args);
            case "spawn":
                return handleSpawn(player);
        }
        return false;
    }

    // Record player's current location as "back" location
    private void updateBackLocation(Player player) {
        backLocations.put(player.getUniqueId(), player.getLocation());
    }

    public boolean isTpaDisabled(Player player) {
        return stateManager.isTpaDisabled(player);
    }
    public void setTpaDisabled(Player player, boolean disabled) {
        stateManager.setTpaDisabled(player, disabled);
    }

    private boolean handleTpa(Player requester, String[] args) {
        if (args.length < 1) {
            requester.sendMessage(languageManager.getMessage("tpa_usage"));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            requester.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            return true;
        }
        // If target has TPA disabled, notify
        if (stateManager.isTpaDisabled(target)) {
            requester.sendMessage(languageManager.getMessage("tpa_disabled"));
            return true;
        }
        teleportRequests.put(
                target.getUniqueId(),
                new TeleportRequest(requester.getUniqueId(), target.getUniqueId(), RequestType.TPA)
        );
        target.sendMessage(languageManager.getMessage("tpa_request_received")
                .replace("{player}", requester.getName()));
        requester.sendMessage(languageManager.getMessage("tpa_request_sent")
                .replace("{player}", target.getName()));
        return true;
    }

    private boolean handleTpahere(Player requester, String[] args) {
        if (args.length < 1) {
            requester.sendMessage(languageManager.getMessage("tpahere_usage"));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            requester.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            return true;
        }
        if (stateManager.isTpaDisabled(target)) {
            requester.sendMessage(languageManager.getMessage("tpa_disabled"));
            return true;
        }
        teleportRequests.put(
                target.getUniqueId(),
                new TeleportRequest(requester.getUniqueId(), target.getUniqueId(), RequestType.TPAHERE)
        );
        target.sendMessage(languageManager.getMessage("tpahere_request_received")
                .replace("{player}", requester.getName()));
        requester.sendMessage(languageManager.getMessage("tpahere_request_sent")
                .replace("{player}", target.getName()));
        return true;
    }

    private boolean handleTpAccept(Player target) {
        TeleportRequest request = teleportRequests.remove(target.getUniqueId());
        if (request == null) {
            target.sendMessage(languageManager.getMessage("tpa_no_request"));
            return true;
        }
        Player requester = Bukkit.getPlayer(request.requesterId);
        if (requester == null) {
            target.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            return true;
        }
        if (request.type == RequestType.TPA) {
            updateBackLocation(requester);
            requester.teleport(target);
            requester.sendMessage(languageManager.getMessage("tpa_teleporting")
                    .replace("{player}", target.getName()));
            target.sendMessage(languageManager.getMessage("tpa_request_accepted"));
        } else {
            // TPAHERE
            updateBackLocation(target);
            target.teleport(requester);
            target.sendMessage(languageManager.getMessage("tpahere_teleporting")
                    .replace("{player}", requester.getName()));
            requester.sendMessage(languageManager.getMessage("tpahere_request_accepted"));
        }
        return true;
    }

    private boolean handleTpDeny(Player target) {
        TeleportRequest request = teleportRequests.remove(target.getUniqueId());
        if (request == null) {
            target.sendMessage(languageManager.getMessage("tpa_no_request"));
            return true;
        }
        Player requester = Bukkit.getPlayer(request.requesterId);
        if (requester != null) {
            requester.sendMessage(languageManager.getMessage("tpa_request_denied")
                    .replace("{player}", target.getName()));
        }
        target.sendMessage(languageManager.getMessage("tpa_request_denied_sent"));
        return true;
    }

    private boolean handleTp(Player sender, String[] args) {
        if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(languageManager.getMessage("tpa_player_not_found"));
                return true;
            }
            updateBackLocation(sender);
            sender.teleport(target);
            sender.sendMessage(languageManager.getMessage("tp_teleported")
                    .replace("{player}", target.getName()));
        } else if (args.length == 2) {
            Player player1 = Bukkit.getPlayer(args[0]);
            Player player2 = Bukkit.getPlayer(args[1]);
            if (player1 == null || player2 == null) {
                sender.sendMessage(languageManager.getMessage("tpa_player_not_found"));
                return true;
            }
            updateBackLocation(player1);
            player1.teleport(player2);
            sender.sendMessage(languageManager.getMessage("tp_teleported_admin")
                    .replace("{player1}", player1.getName())
                    .replace("{player2}", player2.getName()));
        } else {
            sender.sendMessage(languageManager.getMessage("tp_usage"));
            return false;
        }
        return true;
    }

    private boolean handleTphere(Player sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(languageManager.getMessage("tphere_usage"));
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            return true;
        }
        updateBackLocation(target);
        target.teleport(sender);
        sender.sendMessage(languageManager.getMessage("tphere_teleported")
                .replace("{player}", target.getName()));
        target.sendMessage(languageManager.getMessage("tphere_teleported_to")
                .replace("{player}", sender.getName()));
        return true;
    }

    private boolean handleBack(Player player) {
        Location previous = backLocations.get(player.getUniqueId());
        if (previous == null) {
            player.sendMessage(languageManager.getMessage("back_no_location"));
            return true;
        }
        updateBackLocation(player);
        player.teleport(previous);
        player.sendMessage(languageManager.getMessage("back_teleported"));
        return true;
    }

    private boolean handleSetHome(Player player) {
        homeLocations.put(player.getUniqueId(), player.getLocation());
        player.sendMessage(languageManager.getMessage("sethome_set"));
        return true;
    }

    private boolean handleHome(Player player) {
        Location home = homeLocations.get(player.getUniqueId());
        if (home == null) {
            player.sendMessage(languageManager.getMessage("home_not_set"));
            return true;
        }
        updateBackLocation(player);
        player.teleport(home);
        player.sendMessage(languageManager.getMessage("home_teleported"));
        return true;
    }

    private boolean handleSetWarp(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(languageManager.getMessage("setwarp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        warps.put(warpName, player.getLocation());
        player.sendMessage(languageManager.getMessage("setwarp_set").replace("{warp}", warpName));
        return true;
    }

    private boolean handleWarp(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(languageManager.getMessage("warp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        Location warpLoc = warps.get(warpName);
        if (warpLoc == null) {
            player.sendMessage(languageManager.getMessage("warp_not_found").replace("{warp}", warpName));
            return true;
        }
        updateBackLocation(player);
        player.teleport(warpLoc);
        player.sendMessage(languageManager.getMessage("warp_teleported").replace("{warp}", warpName));
        return true;
    }

    private boolean handleDelWarp(Player player, String[] args) {
        if (args.length < 1) {
            player.sendMessage(languageManager.getMessage("delwarp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        if (warps.remove(warpName) != null) {
            player.sendMessage(languageManager.getMessage("delwarp_removed").replace("{warp}", warpName));
        } else {
            player.sendMessage(languageManager.getMessage("warp_not_found").replace("{warp}", warpName));
        }
        return true;
    }

    private boolean handleSpawn(Player player) {
        Location spawnLoc = player.getWorld().getSpawnLocation();
        updateBackLocation(player);
        player.teleport(spawnLoc);
        player.sendMessage(languageManager.getMessage("spawn_teleported"));
        return true;
    }
}
