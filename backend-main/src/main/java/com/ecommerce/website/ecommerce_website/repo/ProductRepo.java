package com.ecommerce.website.ecommerce_website.repo;

import com.ecommerce.website.ecommerce_website.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

//    here we write the JPQL (match the class name properly)
    @Query(
            "SELECT p from Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%',:queryKeyword,'%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%',:queryKeyword,'%')) OR " +
            "LOWER(p.brand) LIKE LOWER(CONCAT('%',:queryKeyword,'%')) OR " +
            "LOWER(p.category) LIKE LOWER(CONCAT('%',:queryKeyword,'%'))"
    )
    List<Product> findByQuery(String queryKeyword);
}
