package com.rabbitmq.registration.user.repository;

import com.rabbitmq.registration.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Spring Data JPA cria a query automaticamente
    Optional<User> findByEmail(String email);
}
