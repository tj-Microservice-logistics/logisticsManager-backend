package com.maxrayyy.userservice.controller;

import com.maxrayyy.userservice.model.Users;
import com.maxrayyy.userservice.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    // 新增用户
    @PostMapping
    public Users createUser(@RequestBody Users user) {
        return usersService.createUser(user.getUsername(), user.getPasswordHash(), user.getRole());
    }

    // 根据用户名查询用户
    @GetMapping("/{username}")
    public Users getUserByUsername(@PathVariable String username) {
        return usersService.getUserByUsername(username);
    }

    // 查询所有用户
    @GetMapping
    public List<Users> getAllUsers() {
        return usersService.getAllUsers();
    }

    // 根据角色查询用户
    @GetMapping("/role/{role}")
    public List<Users> getUsersByRole(@PathVariable Users.Role role) {
        return usersService.getUsersByRole(role);
    }

    // 删除用户
    @DeleteMapping("/{userId}")
    public void deleteUserById(@PathVariable Integer userId) {
        usersService.deleteUserById(userId);
    }
}
