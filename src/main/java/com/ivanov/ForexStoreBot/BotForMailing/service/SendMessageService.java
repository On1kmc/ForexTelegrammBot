package com.ivanov.ForexStoreBot.BotForMailing.service;

import com.ivanov.ForexStoreBot.BotForMailing.ForexBot;
import org.springframework.stereotype.Service;

@Service
public class SendMessageService {

    private final ForexBot forexBot;

    public SendMessageService(ForexBot forexBot) {
        this.forexBot = forexBot;
    }


    public void sendMessage(String text) {
        forexBot.sendMessage(text);
    }
}
