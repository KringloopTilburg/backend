FROM openjdk:21-slim
EXPOSE 8083
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/productimage-service.jar

CMD ["java", "-jar", "productimage-service.jar"]