# 运行阶段
FROM openjdk:17

WORKDIR /app

# 复制本地jar文件
ADD target/user-service-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 9083

# 启动命令 (设置较小的内存限制)
ENTRYPOINT ["java", "-jar", "user-service-0.0.1-SNAPSHOT.jar"]

