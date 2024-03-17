package com.bookdodum.userservice.repository;

import com.bookdodum.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    boolean existsByUserid(String userId);
    Optional<User> findByUserId(String userId);

    Optional<User> findByUserCode(String userCode);

    boolean existsByUserId(String userId);
}
