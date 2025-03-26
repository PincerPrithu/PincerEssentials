package me.pincer.pincerEssentials.managers;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class TeleportManager implements CommandExecutor {
    private final LanguageManager languageManager;

    // Stores teleport requests keyed by the target's UUID.
    private final Map<UUID, TeleportRequest> teleportRequests = new ConcurrentHashMap<>();

    // Stores players' previous locations for /back.
    private final Map<UUID, Location> backLocations = new ConcurrentHashMap<>();

    // Stores home locations (one per player).
    private final Map<UUID, Location> homeLocations = new ConcurrentHashMap<>();

    // Stores warp points, keyed by a lowercase warp name.
    private final Map<String, Location> warps = new ConcurrentHashMap<>();

    // Represents a teleport request type.
    private enum RequestType {
        TPA,      // Requesting to teleport the requester to the target.
        TPAHERE   // Requesting the target to teleport to the requester.
    }

    // Represents a teleport request.
    private static class TeleportRequest {
        UUID requester;
        UUID target;
        RequestType type;

        TeleportRequest(UUID requester, UUID target, RequestType type) {
            this.requester = requester;
            this.target = target;
            this.type = type;
        }
    }

    public TeleportManager() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Only players can use these commands.
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
            default:
                return false;
        }
    }

    // Utility to record the player's current location as their "back" location.
    private void updateBackLocation(Player player) {
        backLocations.put(player.getUniqueId(), player.getLocation());
    }

    // /tpa <player> - Request to teleport to another player.
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
        teleportRequests.put(target.getUniqueId(),
                new TeleportRequest(requester.getUniqueId(), target.getUniqueId(), RequestType.TPA));
        target.sendMessage(languageManager.getMessage("tpa_request_received")
                .replace("{player}", requester.getName()));
        requester.sendMessage(languageManager.getMessage("tpa_request_sent")
                .replace("{player}", target.getName()));
        return true;
    }

    // /tpahere <player> - Request a player to come to you.
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
        teleportRequests.put(target.getUniqueId(),
                new TeleportRequest(requester.getUniqueId(), target.getUniqueId(), RequestType.TPAHERE));
        target.sendMessage(languageManager.getMessage("tpahere_request_received")
                .replace("{player}", requester.getName()));
        requester.sendMessage(languageManager.getMessage("tpahere_request_sent")
                .replace("{player}", target.getName()));
        return true;
    }

    // /tpaccept - Accept a pending teleport request.
    private boolean handleTpAccept(Player target) {
        TeleportRequest request = teleportRequests.remove(target.getUniqueId());
        if (request == null) {
            target.sendMessage(languageManager.getMessage("tpa_no_request"));
            return true;
        }
        Player requester = Bukkit.getPlayer(request.requester);
        if (requester == null) {
            target.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            return true;
        }
        // Different behavior based on request type.
        if (request.type == RequestType.TPA) {
            updateBackLocation(requester);
            requester.teleport(target);
            requester.sendMessage(languageManager.getMessage("tpa_teleporting")
                    .replace("{player}", target.getName()));
            target.sendMessage(languageManager.getMessage("tpa_request_accepted"));
        } else if (request.type == RequestType.TPAHERE) {
            updateBackLocation(target);
            target.teleport(requester);
            target.sendMessage(languageManager.getMessage("tpahere_teleporting")
                    .replace("{player}", requester.getName()));
            requester.sendMessage(languageManager.getMessage("tpahere_request_accepted"));
        }
        return true;
    }

    // /tpdeny - Deny a pending teleport request.
    private boolean handleTpDeny(Player target) {
        TeleportRequest request = teleportRequests.remove(target.getUniqueId());
        if (request == null) {
            target.sendMessage(languageManager.getMessage("tpa_no_request"));
            return true;
        }
        Player requester = Bukkit.getPlayer(request.requester);
        if (requester != null) {
            requester.sendMessage(languageManager.getMessage("tpa_request_denied")
                    .replace("{player}", target.getName()));
        }
        target.sendMessage(languageManager.getMessage("tpa_request_denied_sent"));
        return true;
    }

    // /tp [target] or /tp [player1] [player2] - Direct teleportation.
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
            // Teleport player1 to player2 (typically an admin command).
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

    // /tphere <player> - Teleport a player to your location.
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

    // /back - Teleport you to your previous location.
    private boolean handleBack(Player player) {
        Location previous = backLocations.get(player.getUniqueId());
        if (previous == null) {
            player.sendMessage(languageManager.getMessage("back_no_location"));
            return true;
        }
        // Optionally, update the back location before teleporting.
        updateBackLocation(player);
        player.teleport(previous);
        player.sendMessage(languageManager.getMessage("back_teleported"));
        return true;
    }

    // /sethome - Save your current location as your home.
    private boolean handleSetHome(Player player) {
        homeLocations.put(player.getUniqueId(), player.getLocation());
        player.sendMessage(languageManager.getMessage("sethome_set"));
        return true;
    }

    // /home - Teleport to your saved home.
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

    // /setwarp <name> - Set a warp point at your current location.
    private boolean handleSetWarp(Player sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(languageManager.getMessage("setwarp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        warps.put(warpName, sender.getLocation());
        sender.sendMessage(languageManager.getMessage("setwarp_set")
                .replace("{warp}", warpName));
        return true;
    }

    // /warp <name> - Teleport to a warp point.
    private boolean handleWarp(Player sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(languageManager.getMessage("warp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        Location warpLoc = warps.get(warpName);
        if (warpLoc == null) {
            sender.sendMessage(languageManager.getMessage("warp_not_found")
                    .replace("{warp}", warpName));
            return true;
        }
        updateBackLocation(sender);
        sender.teleport(warpLoc);
        sender.sendMessage(languageManager.getMessage("warp_teleported")
                .replace("{warp}", warpName));
        return true;
    }

    // /delwarp <name> - Delete a warp point.
    private boolean handleDelWarp(Player sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(languageManager.getMessage("delwarp_usage"));
            return false;
        }
        String warpName = args[0].toLowerCase();
        if (warps.remove(warpName) != null) {
            sender.sendMessage(languageManager.getMessage("delwarp_removed")
                    .replace("{warp}", warpName));
        } else {
            sender.sendMessage(languageManager.getMessage("warp_not_found")
                    .replace("{warp}", warpName));
        }
        return true;
    }

    // /spawn - Teleport to the world spawn.
    private boolean handleSpawn(Player sender) {
        Location spawn = sender.getWorld().getSpawnLocation();
        updateBackLocation(sender);
        sender.teleport(spawn);
        sender.sendMessage(languageManager.getMessage("spawn_teleported"));
        return true;
    }
}
