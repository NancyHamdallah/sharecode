# Use an OpenJDK base image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the built JAR file into the image
COPY target/*.jar app.jar

# Set the startup command
ENTRYPOINT ["java", "-jar", "app.jar"]
