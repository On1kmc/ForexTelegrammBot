package com.ivanov.ForexStoreBot.BotForMailing.controller;

import com.ivanov.ForexStoreBot.BotForMailing.Utils;
import com.ivanov.ForexStoreBot.BotForMailing.service.DBQueryService;
import com.ivanov.ForexStoreBot.BotForMailing.service.SendMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/stats/admin")
public class StatController {

    private final DBQueryService dbQueryService;

    private final SendMessageService service;

    public StatController(DBQueryService dbQueryService, SendMessageService service) {
        this.dbQueryService = dbQueryService;
        this.service = service;
    }


    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", dbQueryService.findAllTlgUsers());
        model.addAttribute("forChangeText", Utils.getMessages().get(2) + " {H1 Продукта}." +
                ".\n" + Utils.getMessages().get(3) + " [ссылка Продукта]");
        model.addAttribute("forNewText", Utils.getMessages().get(0) + " {H1 Продукта}." + ".\n" +
                Utils.getMessages().get(1) + " [ссылка Продукта]");
        return "admin";
    }

    @PostMapping("/edit/1")
    public String changeMessage1(@RequestParam(value = "forNewText1", required = false) String text1,
                                 @RequestParam(value = "forNewText2", required = false) String text2) {
        if (text1 != null) {
            Utils.getMessages().set(0, text1);
            Utils.addMessages(0, text1);
        }
        if (text1 != null) {
            Utils.getMessages().set(1, text2);
            Utils.addMessages(1, text2);
        }
        return "redirect:/stats/admin";
    }

    @PostMapping("/edit/2")
    public String changeMessage2(@RequestParam("forChangeText1") String text1, @RequestParam("forChangeText2") String text2) {
        if (text1 != null) {
            Utils.getMessages().set(2, text1);
            Utils.addMessages(2, text1);
        }
        if (text1 != null) {
            Utils.getMessages().set(3, text2);
            Utils.addMessages(3, text2);
        }
        return "redirect:/stats/admin";
    }

    @PostMapping("/sendMessage")
    public String sendMessage(@RequestParam("message") String text) {
        service.sendMessage(text);
        return "redirect:/stats/admin";
    }

}
