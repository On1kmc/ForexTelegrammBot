package com.ivanov.ForexStoreBot.BotForMailing.commands;

import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import com.ivanov.ForexStoreBot.BotForMailing.service.TlgUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class UnsubscribeCommand extends ServiceCommand {

    private static final String identifier = "unsubscribe";

    private static final String description = "unsubscribe for mailing from bot";

    private final TlgUserService tlgUserService;

    UnsubscribeCommand(TlgUserService tlgUserService) {
        super(identifier, description);
        this.tlgUserService = tlgUserService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
        String username = user.getUserName();
        if (username != null) {
            TlgUser tlgUser = tlgUserService.getUser(username.toLowerCase());
            if (tlgUser != null) {
                tlgUserService.deleteUser(tlgUser);
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), "Вы успешно отписались от рассылки");
            } else {
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(), "Вы не подписаны на нашу рассылку");
            }
        }



    }
}
