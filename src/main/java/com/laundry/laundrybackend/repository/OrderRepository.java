package com.laundry.laundrybackend.repository;

import com.laundry.laundrybackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    // Ici tu peux ajouter des méthodes personnalisées si besoin
}

