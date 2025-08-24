package com.github.hav4ik.telegrambridge;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {
    private final FileConfiguration config;

    public Config(FileConfiguration config) {
        this.config = config;
    }

    public String getBotToken() {
        return config.getString("telegram.bot_token", "");
    }

    public long getChatId() {
        return config.getLong("telegram.chat_id", 0);
    }

    public String getMinecraftFormat() {
        return config.getString("formats.minecraft", "§7[TG] §b%player%: §f%message%");
    }

    public String getTelegramFormat() {
        return config.getString("formats.telegram", "👤 %player%: %message%");
    }

    public String getJoinFormat() {
        return config.getString("formats.join", "✅ %player% присоединился к серверу");
    }

    public String getQuitFormat() {
        return config.getString("formats.quit", "❌ %player% покинул сервер");
    }

    public boolean isJoinMessagesEnabled() {
        return config.getBoolean("events.join", true);
    }

    public boolean isQuitMessagesEnabled() {
        return config.getBoolean("events.quit", true);
    }

    public boolean isChatEnabled() {
        return config.getBoolean("events.chat", true);
    }
}