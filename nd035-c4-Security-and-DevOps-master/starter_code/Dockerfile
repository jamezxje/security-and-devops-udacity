# Use a Maven image with Java 1.8
FROM maven:3.8.6-jdk-8 as builder

# Set the working directory in the container
WORKDIR /app

# Copy the Maven project files to the container
COPY pom.xml .
COPY src ./src

# Package the application into a JAR file
RUN mvn clean package

# Use a base JDK 8 image to run the application
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the Maven build stage
COPY --from=builder /app/target/auth-course-0.0.1-SNAPSHOT.jar ./app.jar

# Expose port 8082 for the Spring Boot application
EXPOSE 8082

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]