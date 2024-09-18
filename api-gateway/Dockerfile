FROM openjdk:21-slim
EXPOSE 8080
WORKDIR /app
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /app/apigateway.jar

CMD ["java", "-jar", "apigateway.jar"]