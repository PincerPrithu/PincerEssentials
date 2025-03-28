package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;


public class FixCommand implements CommandExecutor {
    private final LanguageManager lang;

    public FixCommand(LanguageManager lang) {
        this.lang = lang;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String lbl, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(lang.getMessage("command_player_only"));
            return true;
        }
        Player player = (Player) sender;
        if (!player.hasPermission("essentials.fix")) {
            player.sendMessage(lang.getMessage("no_permission"));
            return true;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType().isAir() || !(item.getItemMeta() instanceof Damageable)) {
            player.sendMessage(lang.getMessage("item_not_repairable"));
            return true;
        }

        Damageable meta = (Damageable) item.getItemMeta();
        meta.setDamage(0);
        item.setItemMeta(meta);

        player.sendMessage(lang.getMessage("item_repaired"));
        return true;
    }
}
