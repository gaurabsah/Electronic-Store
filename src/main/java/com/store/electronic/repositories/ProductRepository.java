package com.store.electronic.repositories;

import com.store.electronic.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    Page<Product> findByProductNameContaining(String subtitle, Pageable pageable);

    Page<Product> findByActive(Boolean active, Pageable pageable);
}
