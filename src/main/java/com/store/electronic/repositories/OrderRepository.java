package com.store.electronic.repositories;

import com.store.electronic.entities.Order;
import com.store.electronic.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

    List<Order> findByUser(User user);
}
