FROM openjdk:21-slim
EXPOSE 8080
WORKDIR /app

ARG JAR_FILE=target/authentication.jar
COPY ${JAR_FILE} /app/authentication.jar
CMD ["java", "-jar", "authentication.jar"]
