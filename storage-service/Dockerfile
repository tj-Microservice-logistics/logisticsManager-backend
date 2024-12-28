# 运行阶段
FROM openjdk:17

WORKDIR /app

# 复制本地jar文件
ADD target/storage-service-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 9081

# 启动命令 (设置较小的内存限制)
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "storage-service-0.0.1-SNAPSHOT.jar"]

