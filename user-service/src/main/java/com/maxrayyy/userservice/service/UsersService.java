package com.maxrayyy.userservice.service;

import com.maxrayyy.userservice.model.Users;
import com.maxrayyy.userservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;

    // 新增用户
    public Users createUser(String username, String passwordHash, Users.Role role) {
        Users user = new Users(username, passwordHash, role);
        return usersRepository.save(user); // 保存用户到数据库
    }

    // 根据用户名查询用户
    public Users getUserByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    // 查询所有用户
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    // 查询某个角色的用户
    public List<Users> getUsersByRole(Users.Role role) {
        return usersRepository.findByRole(role);
    }

    // 根据 ID 删除用户
    public void deleteUserById(Integer userId) {
        usersRepository.deleteById(userId);
    }
}
