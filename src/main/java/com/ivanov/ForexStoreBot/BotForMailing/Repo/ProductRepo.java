package com.ivanov.ForexStoreBot.BotForMailing.Repo;

import com.ivanov.ForexStoreBot.BotForMailing.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {


    @Query(nativeQuery = true, value = "SELECT oc.category_id " +
            "FROM oc_product_to_category oc where oc.product_id=:id and oc.main_category=1")
    int findCategoryByProductId(@Param("id") int id);

    @Query(nativeQuery = true, value = "SELECT oc.keyword " +
            "FROM oc_seo_url oc where oc.query=:id")
    String findModelByProductId(@Param("id") String id);

    @Query(nativeQuery = true, value = "SELECT oc.meta_h1 " +
            "FROM oc_product_description oc where oc.product_id=:id")
    String findNameByProductId(@Param("id") int id);

}