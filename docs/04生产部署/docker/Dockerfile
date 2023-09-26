FROM openjdk:17.0-jdk-slim

# 构建参数
ARG JAR_FILE_NAME
ARG SPRING_ENV=prod

# 拷贝jar包
ENV jar_file=${JAR_FILE_NAME}
COPY ${jar_file} /${jar_file}

# 设置spring boot环境, ENTRYPOINT不能直接使用ARG值
ENV SPRING_ACTIVE=${SPRING_ENV}

# 启动
ENTRYPOINT java -jar /${jar_file} --spring.profiles.active=${SPRING_ACTIVE}
