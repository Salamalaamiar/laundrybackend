package com.laundry.laundrybackend.repository;

// 3. Create Repository (LaundryRepository.java)


import com.laundry.laundrybackend.model.Laundry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaundryRepository extends JpaRepository<Laundry, Long> {
    boolean existsByEmail(String email);
    Laundry findByEmail(String email);
}
