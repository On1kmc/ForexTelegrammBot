package com.ivanov.ForexStoreBot.BotForMailing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oc_telegramm_user")
@NoArgsConstructor
@Data
public class TlgUser {

    @Id
    @Column(name ="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @Column(name = "chat_id")
    private long chat_id;

    @Column(name = "username")
    private String username;


    public TlgUser(long chat_id, String username) {
        this.chat_id = chat_id;
        this.username = username;
    }
}
