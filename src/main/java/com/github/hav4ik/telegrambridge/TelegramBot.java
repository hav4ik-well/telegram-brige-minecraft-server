package com.github.hav4ik.telegrambridge;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class TelegramBot extends TelegramLongPollingBot {
    private final Config config;
    private final TelegramBridge plugin;

    public TelegramBot(Config config, TelegramBridge plugin) throws Exception {
        this.config = config;
        this.plugin = plugin;
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);
    }

    @Override
    public String getBotUsername() {
        return "MinecraftBridgeBot";
    }

    @Override
    public String getBotToken() {
        return config.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (plugin.getMessageHandler() != null) {
            plugin.getMessageHandler().handleTelegramMessage(update);
        }
    }

    public void sendMessageToTelegram(String message) {
        if (config.getChatId() == 0) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(config.getChatId()));
        sendMessage.setText(message);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            plugin.getLogger().warning("Ошибка отправки сообщения в Telegram: " + e.getMessage());
        }
    }

    public void shutdown() {
        // Cleanup if needed
    }
}