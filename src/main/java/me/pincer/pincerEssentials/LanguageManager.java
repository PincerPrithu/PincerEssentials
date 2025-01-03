package me.pincer.pincerEssentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

public class LanguageManager {
    private final PincerEssentials plugin;
    private FileConfiguration messagesConfig;
    private File messagesFile;

    public LanguageManager(PincerEssentials plugin) {
        this.plugin = plugin;
        createMessagesFile();
    }

    private void createMessagesFile() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            messagesFile.getParentFile().mkdirs();
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String key) {
        return messagesConfig.getString(key, "Message not found: " + key);
    }

    public void reloadMessages() {
        if (messagesFile == null) {
            messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public void saveMessages() {
        try {
            messagesConfig.save(messagesFile);
        } catch (IOException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not save messages to " + messagesFile, e);
        }
    }
}