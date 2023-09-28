package com.ivanov.ForexStoreBot.BotForMailing.commands;

import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import com.ivanov.ForexStoreBot.BotForMailing.service.DBQueryService;
import com.ivanov.ForexStoreBot.BotForMailing.service.TlgUserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

/*
Команда /start. Здесь описано все, что будет происходить, когда боту напишут эту команду.
 */
@Component
public class StartCommand extends ServiceCommand {
    private static final String identifier = "start";

    private static final String description = "subscribe for mailing";


    private final TlgUserService tlgUserService;

    private final DBQueryService queryService;

        public StartCommand(TlgUserService tlgUserService, DBQueryService queryService) {
            super(identifier, description);
            this.tlgUserService = tlgUserService;
            this.queryService = queryService;
        }

        @Override // Метод, отправляющий ответ пользователю.
        public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
            String userName = user.getUserName(); // берем у пользователя его имя.
            if (userName == null) { // Если оно не заполнено, то говорим ему об этом.
                sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(),
                        "Поле \"username\" в вашем аккаунте не заполнено. Невозможно подписаться на рассылку!\n" +
                                "Заполните профиль и повторите попытку");
            } else { // Если имя есть, то проверяем его в наличии в нашей базе, подписывался ли ранее этот пользователь.
                TlgUser tlgUser = queryService.findByUsername(userName.toLowerCase());
                if (tlgUser == null) { // Если не подписывался, значит добавляем его в бд.
                    tlgUserService.saveNewUser(new TlgUser(chat.getId(), userName.toLowerCase()));
                    // и отправляем ответ, что подписались успешно.
                    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(),
                            "Вы успешно подписались на рассылку!");
                } else { // Если подписывался, присылаем ответ, что уже подписан.
                    sendAnswer(absSender, chat.getId(), this.getCommandIdentifier(),
                            "Вы уже были ранее подписаны на рассылку!");
                }
            }
        }
}
