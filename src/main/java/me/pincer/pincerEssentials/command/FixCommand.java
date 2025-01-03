package me.pincer.pincerEssentials.command;

import me.pincer.pincerEssentials.PincerEssentials;
import me.pincer.pincerEssentials.LanguageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

public class FixCommand implements CommandExecutor {
    private final LanguageManager languageManager;

    public FixCommand() {
        this.languageManager = PincerEssentials.getInstance().getLanguageManager();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return true;
        Player p = (Player) commandSender;
        ItemStack item = p.getInventory().getItemInMainHand();
        if (item == null || !(item.getItemMeta() instanceof Damageable)) {
            p.sendMessage(languageManager.getMessage("item_not_repairable"));
            return true;
        }
        Damageable damageable = (Damageable) item.getItemMeta();
        damageable.setDamage(0);
        item.setItemMeta(damageable);
        p.sendMessage(languageManager.getMessage("item_repaired"));
        return true;
    }
}