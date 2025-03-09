# Use the official Maven image to compile and build the application
FROM maven:3.9.0-eclipse-temurin-17 as build
# Set the working directory inside the container
WORKDIR /app

# Copy the project's pom.xml and source code to the Docker image
COPY pom.xml ./
COPY src ./src

# Build the application (runs Maven package)
RUN mvn clean package -DskipTests

# Use a lightweight JDK image to run the Spring Boot application
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose the port the Spring Boot application will run on
EXPOSE 8080

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]