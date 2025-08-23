package com.example.telegrambridge;

import org.bukkit.plugin.java.JavaPlugin;

public class TelegramBridgePlugin extends JavaPlugin {
    private Config config;
    private TelegramBotHandler telegramBot;
    
    @Override
    public void onEnable() {
        // Сохраняем конфиг по умолчанию
        saveDefaultConfig();
        
        config = new Config(this);
        telegramBot = new TelegramBotHandler(this, config);
        
        // Регистрируем обработчики
        getServer().getPluginManager().registerEvents(new ChatListener(telegramBot), this);
        
        // Инициализируем бота
        telegramBot.initialize();
        
        getLogger().info("Telegram Bridge Plugin enabled!");
    }
    
    @Override
    public void onDisable() {
        if (telegramBot != null) {
            telegramBot.shutdown();
        }
        getLogger().info("Telegram Bridge Plugin disabled!");
    }
    
    public Config getBridgeConfig() {
        return config;
    }
    
    public TelegramBotHandler getTelegramBot() {
        return telegramBot;
    }
}