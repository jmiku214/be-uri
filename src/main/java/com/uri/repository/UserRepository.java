package com.uri.repository;

import com.uri.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByName(String name);
    boolean existsByName(String name);

    boolean existsByEmail(String email);

    User findByEmail(String username);


    @Query(value = "select * from users where roles in :types",nativeQuery = true)
    List<User> findAllByRoleTypeIn(List<String> types);
}