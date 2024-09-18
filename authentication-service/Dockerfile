FROM openjdk:21-slim
EXPOSE 8081
WORKDIR /app

ARG JAR_FILE
COPY ${JAR_FILE} /app/authentication-service.jar
CMD ["java", "-jar", "authentication-service.jar"]
