package org.zerhusen.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerhusen.model.security.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
   Optional<User> findOneByActivationKey(String activationKey);


   List<User> findAllByActivatedIsFalseAndActivationKeyIsNotNullAndCreatedDateBefore(Instant dateTime);


   Optional<User> findOneByResetKey(String resetKey);

   Optional<User> findOneByEmailIgnoreCase(String email);

   Optional<User> findOneByLogin(String login);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesById(Long id);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByLogin(String login);

   @EntityGraph(attributePaths = "authorities")
   Optional<User> findOneWithAuthoritiesByEmailIgnoreCase(String email);

   Page<User> findAllByLoginNot(Pageable pageable, String login);
}
