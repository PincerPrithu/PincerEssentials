package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.UUID;

public class TPACommand implements CommandExecutor {
    private final Map<UUID, UUID> tpaRequests = new ConcurrentHashMap<>();
    private final LanguageManager languageManager;

    public TPACommand() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player p = (Player) sender;

        if (args.length == 0) return false;

        Player target = Bukkit.getPlayer(args[0]);
        if (target != null) {
            tpaRequests.put(target.getUniqueId(), p.getUniqueId());
            target.sendMessage(languageManager.getMessage("tpa_request_received").replace("{player}", p.getName()));
            p.sendMessage(languageManager.getMessage("tpa_request_sent").replace("{player}", target.getName()));
        } else {
            p.sendMessage(languageManager.getMessage("tpa_player_not_found"));
        }
        return true;
    }

    public boolean onTpAccept(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player target = (Player) sender;

        UUID requesterId = tpaRequests.remove(target.getUniqueId());
        if (requesterId != null) {
            Player requester = Bukkit.getPlayer(requesterId);
            if (requester != null) {
                requester.teleport(target);
                target.sendMessage(languageManager.getMessage("tpa_request_accepted"));
                requester.sendMessage(languageManager.getMessage("tpa_teleporting").replace("{player}", target.getName()));
            } else {
                target.sendMessage(languageManager.getMessage("tpa_player_not_found"));
            }
        } else {
            target.sendMessage(languageManager.getMessage("tpa_no_request"));
        }
        return true;
    }
}