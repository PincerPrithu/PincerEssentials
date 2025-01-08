package me.pincer.pincerEssentials;

import me.pincer.pincerEssentials.command.*;
import me.pincer.pincerEssentials.listener.OpenEnderListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class PincerEssentials extends JavaPlugin {
    public static PincerEssentials instance;
    private LanguageManager languageManager;

    @Override
    public void onEnable() {
        instance = this;
        languageManager = new LanguageManager(this);

        OpenEnderCommand openEnderCommand = new OpenEnderCommand();

        getCommand("gms").setExecutor(new GMCommand());
        getCommand("gmc").setExecutor(new GMCommand());
        getCommand("gmsp").setExecutor(new GMCommand());
        getCommand("gma").setExecutor(new GMCommand());
        getCommand("god").setExecutor(new GodModeCommand());
        getCommand("fix").setExecutor(new FixCommand());
        getCommand("trash").setExecutor(new TrashCommand());
        getCommand("openinv").setExecutor(new OpenInventoryCommand());
        getCommand("openender").setExecutor(openEnderCommand);
        getCommand("tpa").setExecutor(new TPACommand());
        getCommand("tpaccept").setExecutor(new TPACommand());

        getServer().getPluginManager().registerEvents(new OpenEnderListener(openEnderCommand.getTaskMap()), this);
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