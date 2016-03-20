package org.zerhusen.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.JwtUserEntity;

/**
 * Created by stephan on 20.03.16.
 */
public interface JwtUserRepository extends JpaRepository<JwtUserEntity, Long> {
    JwtUserEntity findByUsername(String username);
}
