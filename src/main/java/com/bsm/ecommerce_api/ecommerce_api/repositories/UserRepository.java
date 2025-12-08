package com.bsm.ecommerce_api.ecommerce_api.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.bsm.ecommerce_api.ecommerce_api.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
