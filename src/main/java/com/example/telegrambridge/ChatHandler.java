package com.example.telegrambridge;

import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ChatHandler {
    private final TelegramBotHandler telegramBot;
    
    public ChatHandler(TelegramBotHandler telegramBot) {
        this.telegramBot = telegramBot;
    }
    
    public void register() {
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            String username = sender.getGameProfile().getName();
            String content = message.getContent().getString();
            
            telegramBot.sendMessageToTelegram(username, content);
        });
    }
}