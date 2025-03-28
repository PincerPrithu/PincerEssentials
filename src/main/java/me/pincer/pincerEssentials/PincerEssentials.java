package me.pincer.pincerEssentials;

import me.pincer.pincerEssentials.command.*;
import me.pincer.pincerEssentials.listener.OpenEnderListener;
import me.pincer.pincerEssentials.listener.OpenInventoryListener;
import me.pincer.pincerEssentials.managers.TeleportManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class PincerEssentials extends JavaPlugin {

    // Singleton instance
    private static PincerEssentials instance;

    // Managers for messages and ephemeral states (e.g., vanish, TPToggle)
    private LanguageManager languageManager;
    private me.pincer.pincerEssentials.StateManager stateManager;

    @Override
    public void onEnable() {
        instance = this;
        languageManager = new LanguageManager(this);
        stateManager = new me.pincer.pincerEssentials.StateManager(); // Ephemeral states stored in memory

        // Register commands that require state persistence (e.g., teleport/home/warp)
        registerPersistentCommands();

        // Create TeleportManager and register teleport-related commands
        TeleportManager tpManager = new TeleportManager(languageManager, stateManager);
        registerTeleportCommands(tpManager);

        // Register ephemeral commands (non-persistent) such as gamemode, god, vanish, etc.
        registerEphemeralCommands(tpManager);

        // Register openinv / openender commands with their listeners for live-updating inventories

        // OpenInventory command registration and listener
        OpenInventoryCommand openInvCmd = new OpenInventoryCommand(languageManager);
        getCommand("openinv").setExecutor(openInvCmd);
        getServer().getPluginManager().registerEvents(new OpenInventoryListener(openInvCmd.getTaskMap()), this);

        // OpenEnder command registration and listener
        OpenEnderCommand openEnderCmd = new OpenEnderCommand(languageManager);
        getCommand("openender").setExecutor(openEnderCmd);
        getServer().getPluginManager().registerEvents(new OpenEnderListener(openEnderCmd.getTaskMap()), this);
    }

    /**
     * Register commands that require state persistence.
     * (For example, commands that store player locations like home, warp, etc.)
     */
    private void registerPersistentCommands() {
        getCommand("god").setExecutor(new GodModeCommand(languageManager));
        getCommand("fix").setExecutor(new FixCommand(languageManager));
        getCommand("trash").setExecutor(new TrashCommand(languageManager));
    }

    /**
     * Register teleport-related commands that store state in memory.
     *
     * @param tpManager The TeleportManager instance.
     */
    private void registerTeleportCommands(TeleportManager tpManager) {
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
    }

    /**
     * Register ephemeral (non-persistent) commands that perform immediate actions.
     * (These commands do not store data to disk or require long-term state.)
     *
     * @param tpManager The TeleportManager instance.
     */
    private void registerEphemeralCommands(TeleportManager tpManager) {
        // Gamemode commands are ephemeral
        getCommand("gms").setExecutor(new GMCommand(languageManager));
        getCommand("gmc").setExecutor(new GMCommand(languageManager));
        getCommand("gmsp").setExecutor(new GMCommand(languageManager));
        getCommand("gma").setExecutor(new GMCommand(languageManager));

        // Other ephemeral commands
        getCommand("vanish").setExecutor(new VanishCommand(languageManager, stateManager));
        getCommand("speed").setExecutor(new SpeedCommand(languageManager));
        getCommand("fly").setExecutor(new FlyCommand(languageManager));
        getCommand("kill").setExecutor(new KillCommand(languageManager));
        getCommand("feed").setExecutor(new FeedCommand(languageManager));
        getCommand("heal").setExecutor(new HealCommand(languageManager));
        getCommand("top").setExecutor(new TopCommand(languageManager));
        getCommand("jump").setExecutor(new JumpCommand(languageManager));
        getCommand("ping").setExecutor(new PingCommand(languageManager));
        getCommand("me").setExecutor(new MeCommand(languageManager));
        getCommand("broadcast").setExecutor(new BroadcastCommand(languageManager));
        getCommand("tptoggle").setExecutor(new TPToggleCommand(languageManager, tpManager));
        getCommand("lightning").setExecutor(new LightningCommand(languageManager));
        getCommand("ice").setExecutor(new IceCommand(languageManager));
        getCommand("item").setExecutor(new ItemCommand(languageManager));
        getCommand("clearinventory").setExecutor(new ClearInventoryCommand(languageManager));
        getCommand("fireball").setExecutor(new FireballCommand(languageManager));
        getCommand("depth").setExecutor(new DepthCommand(languageManager));
    }

    public static PincerEssentials getInstance() {
        return instance;
    }

    public LanguageManager getLanguageManager() {
        return languageManager;
    }

    public me.pincer.pincerEssentials.StateManager getStateManager() {
        return stateManager;
    }
}
