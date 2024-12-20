package com.maxrayyy.userservice.repository;

import com.maxrayyy.userservice.model.Users;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    // 根据用户名查询用户
    Users findByUsername(String username);

    // 查询所有指定角色的用户
    List<Users> findByRole(Users.Role role);

    @Override
    <S extends Users> List<S> findAll(Example<S> example);
}
