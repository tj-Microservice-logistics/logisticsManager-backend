package com.maxrayyy.userservice.model;

import com.maxrayyy.userservice.config.RoleConverter;
import jakarta.persistence.*;
import org.hibernate.annotations.ColumnTransformer;

@Entity
@Table(name = "users") // 映射到数据库表 users
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 主键自增
    @Column(name = "user_id") // 映射 user_id 列
    private Integer userId;

    @Column(name = "username", nullable = false, unique = true, length = 50) // 映射 username 列
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255) // 映射 password_hash 列
    private String passwordHash;

    @Convert(converter = RoleConverter.class)  // 使用枚举类型存储为字符串
    @Column(name = "role", nullable = false) // 映射 role 列
    private Role role;

    // 无参构造方法
    public Users() {}

    // 带参构造方法
    public Users(String username, String passwordHash, Role role) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    // Getters 和 Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    // 枚举类 Role
    public enum Role {
        MAIN_MANAGER, // 对应数据库的 main_manager
        WAREHOUSE_MANAGER // 对应数据库的 warehouse_manager
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", role=" + role +
                '}';
    }
}
