package com.ivanov.ForexStoreBot.BotForMailing.service;

import com.ivanov.ForexStoreBot.BotForMailing.Repo.ProductRepo;
import com.ivanov.ForexStoreBot.BotForMailing.Repo.TlgRepo;
import com.ivanov.ForexStoreBot.BotForMailing.Repo.UserRepo;
import com.ivanov.ForexStoreBot.BotForMailing.model.Product;
import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DBQueryService {
    private final UserRepo userRepo;

    private final TlgRepo tlgRepo;

    private final ProductRepo productRepo;

    public DBQueryService(UserRepo userRepo, TlgRepo tlgRepo, ProductRepo productRepo) {
        this.userRepo = userRepo;
        this.tlgRepo = tlgRepo;
        this.productRepo = productRepo;
    }


    public List<String> findInfoAboutUserByProductId(int id) {
        return userRepo.findInfoAboutUserByProductId(id);
    }

    public int findCategoryByProductId(int id) {
        return productRepo.findCategoryByProductId(id);
    }

    public List<TlgUser> findAllTlgUsers() {
        return tlgRepo.findAll();
    }

    public TlgUser findByUsername(String username) {
        return tlgRepo.findByUsername(username);
    }

    public String findProductById(int id) {
        String product_id = "product_id=" + id;
        return productRepo.findModelByProductId(product_id);
    }

    public String findNameByProductId(int id) {
        return productRepo.findNameByProductId(id);
    }


}
