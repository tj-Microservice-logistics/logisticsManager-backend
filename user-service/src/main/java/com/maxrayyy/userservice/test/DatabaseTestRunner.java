package com.maxrayyy.userservice.test;

import com.maxrayyy.userservice.model.Users;
import com.maxrayyy.userservice.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component // 表示这是一个 Spring 管理的 Bean
public class DatabaseTestRunner implements CommandLineRunner {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public void run(String... args) throws Exception {
        // 插入测试数据
//        Users user = new Users("john_doe", "hashed_password", Users.Role.MAIN_MANAGER);
//        usersRepository.save(user); // 保存到数据库
//        System.out.println("用户已保存: " + user);

        // 查询所有用户
        System.out.println("所有用户: " + usersRepository.findAll());
    }
}
