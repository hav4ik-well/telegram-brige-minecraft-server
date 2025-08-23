package com.example.telegrambridge;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class TelegramBotHandler {
    private TelegramBot bot;
    private final Config config;
    private final JavaPlugin plugin;
    
    public TelegramBotHandler(JavaPlugin plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }
    
    public void initialize() {
        if (config.botToken.equals("YOUR_BOT_TOKEN")) {
            plugin.getLogger().warning("Please set up your Telegram bot token in config.yml!");
            return;
        }
        
        bot = new TelegramBot(config.botToken);
        bot.setUpdatesListener(new UpdatesListener() {
            @Override
            public int process(List<Update> updates) {
                for (Update update : updates) {
                    if (update.message() != null && update.message().text() != null) {
                        handleMessage(update);
                    }
                }
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        });
    }
    
    private void handleMessage(Update update) {
        if (update.message().chat().id() == config.chatId) {
            String username = update.message().from().firstName();
            if (update.message().from().username() != null) {
                username += " (@" + update.message().from().username() + ")";
            }
            
            String message = update.message().text();
            String formattedMessage = String.format(config.formatFromTG, username, message);
            
            Bukkit.getScheduler().runTask(plugin, () -> {
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', formattedMessage));
            });
        }
    }
    
    public void sendMessageToTelegram(String username, String message) {
        if (bot != null && config.chatId != 0) {
            String formattedMessage = String.format(config.formatFromMC, username, message);
            SendMessage request = new SendMessage(config.chatId, formattedMessage);
            
            Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
                try {
                    bot.execute(request);
                } catch (Exception e) {
                    plugin.getLogger().warning("Failed to send message to Telegram: " + e.getMessage());
                }
            });
        }
    }
    
    public void shutdown() {
        if (bot != null) {
            bot.removeGetUpdatesListener();
        }
    }
}