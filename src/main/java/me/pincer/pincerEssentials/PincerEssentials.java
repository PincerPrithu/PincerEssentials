package me.pincer.pincerEssentials;

import me.pincer.pincerEssentials.command.*;
import me.pincer.pincerEssentials.listener.OpenEnderListener;
import me.pincer.pincerEssentials.listener.OpenInventoryListener;
import me.pincer.pincerEssentials.managers.TeleportManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PincerEssentials extends JavaPlugin {
    public static PincerEssentials instance;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        instance = this;
        languageManager = new LanguageManager(this);

        OpenEnderCommand openEnderCommand = new OpenEnderCommand();
        OpenInventoryCommand openInventoryCommand = new OpenInventoryCommand();

        getCommand("gms").setExecutor(new GMCommand());
        getCommand("gmc").setExecutor(new GMCommand());
        getCommand("gmsp").setExecutor(new GMCommand());
        getCommand("gma").setExecutor(new GMCommand());
        getCommand("god").setExecutor(new GodModeCommand());
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("openinv").setExecutor(openInventoryCommand);
        getCommand("openender").setExecutor(openEnderCommand);

        TeleportManager tpManager = new TeleportManager();
        // Register all teleport commands to use the TeleportManager.
        getCommand("tpa").setExecutor(tpManager);
        getCommand("tpahere").setExecutor(tpManager);
        getCommand("tpaccept").setExecutor(tpManager);
        getCommand("tpdeny").setExecutor(tpManager);
        getCommand("tp").setExecutor(tpManager);
        getCommand("tphere").setExecutor(tpManager);
        getCommand("back").setExecutor(tpManager);
        getCommand("sethome").setExecutor(tpManager);
        getCommand("home").setExecutor(tpManager);
        getCommand("setwarp").setExecutor(tpManager);
        getCommand("warp").setExecutor(tpManager);
        getCommand("delwarp").setExecutor(tpManager);
        getCommand("spawn").setExecutor(tpManager);

        getServer().getPluginManager().registerEvents(new OpenEnderListener(openEnderCommand.getTaskMap()), this);
        getServer().getPluginManager().registerEvents(new OpenInventoryListener(openInventoryCommand.getTaskMap()), this);
    }

    public static PincerEssentials getInstance() {
        return instance;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    @Override
    public void onDisable() {
    }
}