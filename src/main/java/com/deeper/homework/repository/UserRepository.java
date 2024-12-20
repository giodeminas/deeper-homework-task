package com.deeper.homework.repository;

import com.deeper.homework.entity.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
  User findByUsername(String username);

  boolean existsByUsername(String username);
}
