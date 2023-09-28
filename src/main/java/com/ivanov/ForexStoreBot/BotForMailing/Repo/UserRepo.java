package com.ivanov.ForexStoreBot.BotForMailing.Repo;

import com.ivanov.ForexStoreBot.BotForMailing.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    @Query(nativeQuery = true, value = "SELECT oc.custom_field " +
            "FROM oc_customer oc where oc.customer_id IN (select oo.customer_id FROM oc_order oo where oo.order_id IN" +
            "(select op.order_id FROM oc_order_product op where op.product_id=:id))")
    List<String> findInfoAboutUserByProductId(@Param("id") long id);


}
