package com.ivanov.ForexStoreBot.BotForMailing.service;

import com.ivanov.ForexStoreBot.BotForMailing.Repo.TlgRepo;
import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TlgUserService {

    private final TlgRepo tlgRepo;


    public TlgUserService(TlgRepo tlgRepo) {
        this.tlgRepo = tlgRepo;
    }


    @Transactional
    public void saveNewUser(TlgUser tlgUser) {
        tlgRepo.save(tlgUser);
    }


    public TlgUser getUser(String username) {
        return tlgRepo.findByUsername(username);
    }

    @Transactional
    public void deleteUser(TlgUser tlgUser) {
        tlgRepo.delete(tlgUser);
    }

}
