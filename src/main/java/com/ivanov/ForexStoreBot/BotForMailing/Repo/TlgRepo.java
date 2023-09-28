package com.ivanov.ForexStoreBot.BotForMailing.Repo;

import com.ivanov.ForexStoreBot.BotForMailing.model.TlgUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TlgRepo extends JpaRepository<TlgUser, Integer> {

    TlgUser findByUsername(String username);

}
