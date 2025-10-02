package com.pandorian.tdd_bdd.repository;

import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pandorian.tdd_bdd.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);

    boolean existsById(@NonNull Long id);

    Optional<User> findByUsername(String username);
}
