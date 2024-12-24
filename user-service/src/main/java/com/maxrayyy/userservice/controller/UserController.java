package com.maxrayyy.userservice.controller;

import com.maxrayyy.userservice.dto.LoginRequest;
import com.maxrayyy.userservice.repository.UsersRepository;
import com.maxrayyy.userservice.model.Users;
import com.maxrayyy.commonmodule.entity.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // 确保这是 /users
public class UserController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/login") // 确保这是 /login
    public String login(@RequestBody LoginRequest request) {
        // 查找用户
        Users user = usersRepository.findByUsername(request.getUsername());
        if (user == null || !user.getPasswordHash().equals(request.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 返回 JWT Token
        return JwtUtil.getToken(user.getUserId().toString());
    }
}
