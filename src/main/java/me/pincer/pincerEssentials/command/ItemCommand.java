package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class ItemCommand implements CommandExecutor {
    private final LanguageManager lang;

    public ItemCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
        if (!sender.hasPermission("essentials.item")) {
            sender.sendMessage(lang.getMessage("no_permission"));
            return true;
        }
        if (!(sender instanceof Player)) {
            sender.sendMessage("&cConsole must specify a player. Usage: /item <material> [amount]");
            return true;
        }
        Player player = (Player) sender;

        if (args.length < 1 || args.length > 2) {
            player.sendMessage(lang.getMessage("item_invalid"));
            return true;
        }

        Material mat = Material.getMaterial(args[0].toUpperCase());
        if (mat == null) {
            player.sendMessage(lang.getMessage("item_invalid"));
            return true;
        }

        int amount = 1;
        if (args.length == 2) {
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.sendMessage(lang.getMessage("item_invalid"));
                return true;
            }
        }
        if (amount < 1) amount = 1;
        if (amount > mat.getMaxStackSize()) {
            amount = mat.getMaxStackSize();
        }

        ItemStack stack = new ItemStack(mat, amount);
        player.getInventory().addItem(stack);

        player.sendMessage(lang.getMessage("item_given_self")
                .replace("{material}", mat.name())
                .replace("{amount}", String.valueOf(amount)));
        return true;
    }
}
