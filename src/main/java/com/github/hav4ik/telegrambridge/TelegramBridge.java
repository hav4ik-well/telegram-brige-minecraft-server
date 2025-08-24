package com.github.hav4ik.telegrambridge;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class TelegramBridge extends JavaPlugin implements Listener {

    private TelegramBot telegramBot;
    private Config config;
    private MessageHandler messageHandler;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = new Config(getConfig());
        
        try {
            telegramBot = new TelegramBot(config, this);
            messageHandler = new MessageHandler(this, config);
            getLogger().info("Telegram бот успешно запущен!");
        } catch (Exception e) {
            getLogger().severe("Ошибка при запуске Telegram бота: " + e.getMessage());
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }

        Bukkit.getPluginManager().registerEvents(this, this);
        getLogger().info("Плагин TelegramBridge успешно запущен!");
    }

    @Override
    public void onDisable() {
        if (telegramBot != null) {
            telegramBot.shutdown();
        }
        getLogger().info("Плагин TelegramBridge выключен!");
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        if (messageHandler != null) {
            messageHandler.sendChatMessage(event.getPlayer().getName(), event.getMessage());
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (messageHandler != null) {
            messageHandler.sendPlayerJoin(event.getPlayer().getName());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (messageHandler != null) {
            messageHandler.sendPlayerQuit(event.getPlayer().getName());
        }
    }

    public TelegramBot getTelegramBot() {
        return telegramBot;
    }

    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}