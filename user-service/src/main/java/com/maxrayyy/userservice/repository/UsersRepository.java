package com.maxrayyy.userservice.repository;

import com.maxrayyy.userservice.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {

    // 根据用户名查找用户
    Optional<Users> findByUsername(String username);
}
