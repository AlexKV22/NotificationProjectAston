FROM eclipse-temurin:18-jre
WORKDIR /app
COPY target/notification-microservice-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app.jar"]