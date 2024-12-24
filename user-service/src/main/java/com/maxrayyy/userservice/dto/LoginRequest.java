package com.maxrayyy.userservice.dto;

public class LoginRequest {
    private String username; // 用户名
    private String password; // 密码

    // 无参构造函数
    public LoginRequest() {}

    // 全参构造函数
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters 和 Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
