package com.laundry.laundrybackend.repository;

import com.laundry.laundrybackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // NEW METHODS - Enhanced functionality
    List<Order> findByClientIdAndStatus(Long clientId, String status);

    List<Order> findByClientIdAndStatusIn(Long clientId, List<String> statuses);

    List<Order> findByClientIdOrderByCreatedAtDesc(Long clientId);

    Order findByOrderIdAndClientId(Long orderId, Long clientId);

    @Query("SELECT o FROM Order o WHERE o.clientId = :clientId AND o.status IN ('processing', 'washing', 'drying', 'ironing', 'ready') ORDER BY o.createdAt DESC")
    List<Order> findActiveOrdersByClientId(@Param("clientId") Long clientId);

    @Query("SELECT o FROM Order o WHERE o.clientId = :clientId AND o.status = 'pending' ORDER BY o.createdAt DESC")
    List<Order> findPendingOrdersByClientId(@Param("clientId") Long clientId);
}
