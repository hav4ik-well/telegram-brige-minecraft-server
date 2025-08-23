package com.example.telegrambridge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;

public class TelegramBridgeMod implements ModInitializer {
    private Config config;
    private TelegramBotHandler telegramBot;
    private ChatHandler chatHandler;
    
    @Override
    public void onInitialize() {
        config = Config.load();
        telegramBot = new TelegramBotHandler(config);
        chatHandler = new ChatHandler(telegramBot);
        
        ServerLifecycleEvents.SERVER_STARTING.register(this::onServerStarting);
        ServerLifecycleEvents.SERVER_STOPPED.register(this::onServerStopped);
        
        chatHandler.register();
        System.out.println("Telegram Bridge Mod initialized!");
    }
    
    private void onServerStarting(MinecraftServer server) {
        telegramBot.initialize(server);
    }
    
    private void onServerStopped(MinecraftServer server) {
        telegramBot.shutdown();
    }
}