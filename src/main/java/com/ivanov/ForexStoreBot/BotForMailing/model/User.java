package com.ivanov.ForexStoreBot.BotForMailing.model;


import jakarta.persistence.*;

@Entity
@Table(name = "oc_customer")
public class User {

    @Id
    @Column(name ="customer_id")
    private int id;

    @Column(name = "custom_field")
    private String info;


}
