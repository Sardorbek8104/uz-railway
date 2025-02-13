# Use the official OpenJDK image as a base
FROM openjdk:21-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file to the container
COPY build/libs/eureka-server-0.0.1-SNAPSHOT.jar app.jar

# Expose the port your Spring Boot app listens on (default is 8080)
EXPOSE 8761

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
