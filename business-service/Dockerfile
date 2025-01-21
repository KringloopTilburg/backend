FROM openjdk:21-slim
EXPOSE 8080
WORKDIR /app

ARG JAR_FILE
COPY ${JAR_FILE} /app/businessservice.jar
CMD ["java", "-jar", "businessservice.jar"]