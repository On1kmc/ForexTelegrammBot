package com.ivanov.ForexStoreBot.BotForMailing;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivanov.ForexStoreBot.BotForMailing.commands.StartCommand;
import com.ivanov.ForexStoreBot.BotForMailing.commands.UnsubscribeCommand;
import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import com.ivanov.ForexStoreBot.BotForMailing.service.DBQueryService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/*
Created by @On1k_mc.
 */

/*
В проекте использовано 2 бота, которых я запустил на канал. Первый бот нужен для того, чтобы информировать второго.

Итак, на сайте имеем кнопки, по нажатии которых первый бот отправляет сообщение в канал о том, что произошло какое то событие.

Второй бот это видит и уже решает что делать с этой информацией.
 */
@Component
public class ForexBot extends TelegramLongPollingCommandBot {
    private final DBQueryService queryService;

    public ForexBot(StartCommand startCommand, DBQueryService queryService, UnsubscribeCommand unsubscribeCommand) {
        super();
        this.queryService = queryService;
        register(startCommand);
        register(unsubscribeCommand);
        List<BotCommand> list = new ArrayList<>();
        list.add(new BotCommand("start", "Старт"));
        list.add(new BotCommand("unsubscribe", "Отписаться"));
        try {
            this.execute(new SetMyCommands(list, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException ignored) {
        }
        try {
            Utils.initializeCategories();
            Utils.initializeMessages();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
    @Override
    public String getBotUsername() {
        return "FOREX ROBOT STORE";
    }

    @Override
    public String getBotToken() {
        return Utils.getToken();
    }

    @Override
    public void processNonCommandUpdate(Update update) {

        // Если в нашем канале forex-bots присылается сообщение
        Message channelPost = update.getChannelPost();
        if (channelPost != null) {
            long chatId = channelPost.getChatId();
            if (Long.parseLong("-1001891939142") == chatId) {
                String[] requests = channelPost.getText().split(" ");
                int a = Integer.parseInt(requests[1]);
                switch (requests[0]) {
                    // если товар изменился и в канале появилось "change"
                    case "change" -> changeHandler(a);
                    // если  добавили новый товар и в канале появилось "new"
                    case "new" -> newHandler(a);
                }
            }
        }
    }


    private void setAnswer(Long chatId, String text) {
        //Метод для отправки одного сообщения.
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException ignored) {
        }
    }

    private void changeHandler(int id) { // Метод, который срабатывает при появлении информации об изменении товара.
        List<String> list = queryService.findInfoAboutUserByProductId(id);// список, состоящий из полей БД, содержащих
                                                                            // имя пользователя, купивших такой товар.
        String UrlOfProduct = queryService.findProductById(id); // Берем из БД URL продукта для генерации ссылки
        int categoryId = queryService.findCategoryByProductId(id); // Берем из БД категорию продукта для генерации ссылки
        String productName = queryService.findNameByProductId(id);
        ObjectMapper mapper = new ObjectMapper();
        List<TlgUser> users = queryService.findAllTlgUsers();
        for (String value : list) {
            // Тут в цикле обходим всех пользователей, которые купили товар
            try {
                JsonNode obj = mapper.readTree(value);
                JsonNode node = obj.get("4");// Достаем имя пользователя телеграмм.
                if (node == null) continue;
                String name = node.toString();
                String username = name.substring(2, name.length() - 1).toLowerCase();
                long userChatId = 0;
                if (users.stream().map(TlgUser::getUsername).filter(s -> s.equals(username)).toList().size() == 1) {
                    List<Long> longs = users.stream().filter(s -> s.getUsername().equals(username)).map(TlgUser::getChat_id).toList();
                    userChatId = longs.get(0);    // Достаем id чата с пользователем, если он подписан на бота.
                }
                if (userChatId == 0) continue;
                String link = Utils.getLink(Utils.getCategories().get(categoryId), UrlOfProduct);
                setAnswer(userChatId, Utils.getMessages().get(2) + " " + productName +
                        ".\n" + Utils.getMessages().get(3) + link); // Отправляем информацию
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void newHandler(int id) {
        List<TlgUser> users = queryService.findAllTlgUsers(); // Получаем список всех юзеров, подписанных на бота.
        String modelOfProduct = queryService.findProductById(id); // Берем из БД модель продукта для генерации ссылки
        String productName = queryService.findNameByProductId(id);
        int categoryId = queryService.findCategoryByProductId(id); // Берем из БД категорию продукта для генерации ссылки
        String link = Utils.getLink(Utils.getCategories().get(categoryId), modelOfProduct);
        for (TlgUser user : users) { // отправляем сообщение каждому пользователю, подписанному на бота.
            setAnswer(user.getChat_id(),
                    Utils.getMessages().get(0) + productName + ".\n" +
                            Utils.getMessages().get(1) + link);
        }
    }

    public void sendMessage(String text) {
        List<TlgUser> users = queryService.findAllTlgUsers();
        users.forEach(s -> {
            setAnswer(s.getChat_id(), text);
        });
    }
}
