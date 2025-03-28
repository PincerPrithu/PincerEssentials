package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;


public class TrashCommand implements CommandExecutor {
    private final LanguageManager lang;

    public TrashCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.trash")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        Inventory trashInv = Bukkit.createInventory(null, 54, lang.getMessage("trash_inventory_title"));
        player.openInventory(trashInv);
        return true;
    }
}
