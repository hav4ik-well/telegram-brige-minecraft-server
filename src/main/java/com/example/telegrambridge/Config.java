package com.example.telegrambridge;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {
    private final JavaPlugin plugin;
    
    public String botToken;
    public long chatId;
    public String formatFromMC;
    public String formatFromTG;
    
    public Config(JavaPlugin plugin) {
        this.plugin = plugin;
        load();
    }
    
    public void load() {
        plugin.saveDefaultConfig();
        FileConfiguration config = plugin.getConfig();
        
        botToken = config.getString("bot-token", "YOUR_BOT_TOKEN");
        chatId = config.getLong("chat-id", 0L);
        formatFromMC = config.getString("format.from-mc", "[Minecraft] %s: %s");
        formatFromTG = config.getString("format.from-tg", "§a[Telegram] §f%s: %s");
    }
    
    public void save() {
        FileConfiguration config = plugin.getConfig();
        
        config.set("bot-token", botToken);
        config.set("chat-id", chatId);
        config.set("format.from-mc", formatFromMC);
        config.set("format.from-tg", formatFromTG);
        
        plugin.saveConfig();
    }
}