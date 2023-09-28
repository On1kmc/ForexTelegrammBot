package com.ivanov.ForexStoreBot.BotForMailing.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "oc_product")
@Data
@NoArgsConstructor
public class Product {

    @Id
    @Column(name ="product_id")
    private int id;

    @Column(name = "model")
    private String model;
}
