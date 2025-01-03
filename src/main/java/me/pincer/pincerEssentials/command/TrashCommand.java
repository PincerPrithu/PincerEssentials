package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class TrashCommand implements CommandExecutor {
    private final LanguageManager languageManager;

    public TrashCommand() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;

        Inventory inventory = Bukkit.createInventory(null, 54, languageManager.getMessage("trash_inventory_title"));

        p.openInventory(inventory);

        return true;
    }
}