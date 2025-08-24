package com.github.hav4ik.telegrambridge;

import org.bukkit.Bukkit;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MessageHandler {

    private final TelegramBridge plugin;
    private final Config config;

    public MessageHandler(TelegramBridge plugin, Config config) {
        this.plugin = plugin;
        this.config = config;
    }

    public void handleTelegramMessage(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            long chatId = message.getChatId();

            // Проверяем, что сообщение пришло из нужного чата
            if (chatId == config.getChatId()) {
                String text = message.getText();
                String senderName = getSenderName(message);

                // Форматируем сообщение для Minecraft
                String formattedMessage = config.getTelegramFormat()
                        .replace("%player%", senderName)
                        .replace("%message%", text);

                // Отправляем сообщение в игру (синхронно)
                Bukkit.getScheduler().runTask(plugin, () -> {
                    Bukkit.broadcastMessage(formattedMessage);
                });
            }
        }
    }

    private String getSenderName(Message message) {
        String sender = message.getFrom().getFirstName();
        
        // Добавляем username если есть
        if (message.getFrom().getUserName() != null && !message.getFrom().getUserName().isEmpty()) {
            sender += " (@" + message.getFrom().getUserName() + ")";
        }
        
        return sender;
    }

    public void sendToTelegram(String message) {
        if (config.getChatId() != 0 && plugin.getTelegramBot() != null) {
            plugin.getTelegramBot().sendMessageToTelegram(message);
        }
    }

    public void sendPlayerJoin(String playerName) {
        if (config.isJoinMessagesEnabled()) {
            String message = config.getJoinFormat().replace("%player%", playerName);
            sendToTelegram(message);
        }
    }

    public void sendPlayerQuit(String playerName) {
        if (config.isQuitMessagesEnabled()) {
            String message = config.getQuitFormat().replace("%player%", playerName);
            sendToTelegram(message);
        }
    }

    public void sendChatMessage(String playerName, String messageText) {
        if (config.isChatEnabled()) {
            String message = config.getMinecraftFormat()
                    .replace("%player%", playerName)
                    .replace("%message%", messageText);
            sendToTelegram(message);
        }
    }
}