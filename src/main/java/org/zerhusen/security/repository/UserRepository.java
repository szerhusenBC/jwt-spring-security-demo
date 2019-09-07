package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.User;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
