# Use the official Eclipse Temurin Java 21 JDK with Alpine Linux (lightweight)
FROM eclipse-temurin:21-jdk-alpine

# Set environment variables
ENV TZ=Asia/Kolkata \
    LANG='en_US.UTF-8' \
    LANGUAGE='en_US:en'

# Create a directory inside the container for the app
WORKDIR /app

# Copy the jar file into the container
COPY target/myportfolio-0.0.1-SNAPSHOT.jar app.jar

# Expose port (optional - helps with documentation)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
