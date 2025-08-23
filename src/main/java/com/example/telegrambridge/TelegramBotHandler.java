package com.example.telegrambridge;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;

import java.util.List;

public class TelegramBotHandler {
    private TelegramBot bot;
    private final Config config;
    private MinecraftServer server;
    
    public TelegramBotHandler(Config config) {
        this.config = config;
    }
    
    public void initialize(MinecraftServer server) {
        this.server = server;
        if (config.botToken.equals("YOUR_BOT_TOKEN")) {
            System.out.println("Please set up your Telegram bot token in config!");
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
            
            if (server != null) {
                server.getPlayerManager().broadcast(Text.literal(formattedMessage), false);
            }
        }
    }
    
    public void sendMessageToTelegram(String username, String message) {
        if (bot != null && config.chatId != 0) {
            String formattedMessage = String.format(config.formatFromMC, username, message);
            SendMessage request = new SendMessage(config.chatId, formattedMessage);
            bot.execute(request);
        }
    }
    
    public void shutdown() {
        if (bot != null) {
            bot.removeGetUpdatesListener();
        }
    }
}