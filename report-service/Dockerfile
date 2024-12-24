# 运行阶段
FROM openjdk:17-jdk-slim

WORKDIR /app

# 设置时区
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

# 创建报表存放的目录
RUN mkdir -p /app/files/reports

# 复制本地jar文件
ADD target/report-service-0.0.1-SNAPSHOT.jar .

# 暴露端口
EXPOSE 9084

# 启动命令 (设置较小的内存限制)
ENTRYPOINT ["java", "-Xmx256m", "-Xms128m", "-jar", "report-service-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=prod"]

