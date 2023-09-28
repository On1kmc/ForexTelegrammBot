package com.ivanov.ForexStoreBot.BotForMailing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class IndexController {

    public String index() {
        return "index";
    }
}
