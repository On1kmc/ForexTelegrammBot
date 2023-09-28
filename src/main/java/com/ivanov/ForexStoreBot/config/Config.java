package com.ivanov.ForexStoreBot.config;

import com.ivanov.ForexStoreBot.BotForMailing.ForexBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class Config {

    public final ForexBot forexBot;

    public Config(ForexBot forexBot) {
        this.forexBot = forexBot;
    }

    @Bean
    public TelegramBotsApi bot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(forexBot);
        return botsApi;
    }





}
