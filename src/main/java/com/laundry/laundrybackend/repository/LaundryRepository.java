package com.laundry.laundrybackend.repository;

// 3. Create Repository (LaundryRepository.java)


import com.laundry.laundrybackend.model.Laundry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;



@Repository
public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    Optional<Laundry> findByEmail(String email);
    boolean existsByEmail(String email);

    List<Laundry> findByIsActivatedTrue();

}