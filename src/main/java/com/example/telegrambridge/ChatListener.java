package com.example.telegrambridge;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {
    private final TelegramBotHandler telegramBot;
    
    public ChatListener(TelegramBotHandler telegramBot) {
        this.telegramBot = telegramBot;
    }
    
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String username = event.getPlayer().getName();
        String message = event.getMessage();
        
        telegramBot.sendMessageToTelegram(username, message);
    }
}