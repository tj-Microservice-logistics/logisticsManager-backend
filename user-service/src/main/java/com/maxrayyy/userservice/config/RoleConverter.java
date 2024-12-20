package com.maxrayyy.userservice.config;

import com.maxrayyy.userservice.model.Users;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true) // 自动应用到所有使用的枚举字段
public class RoleConverter implements AttributeConverter<Users.Role, String> {

    @Override
    public String convertToDatabaseColumn(Users.Role role) {
        if (role == null) {
            return null;
        }
        // 将枚举值的名称转换为大写
        return role.name().toUpperCase();
    }

    @Override
    public Users.Role convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        // 从数据库读取时，将大写值转换为枚举
        return Users.Role.valueOf(dbData.toUpperCase());
    }
}
