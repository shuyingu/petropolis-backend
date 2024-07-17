# First stage: build stage
FROM maven:3.9.7-eclipse-temurin-22 AS build
WORKDIR /app
# Copy the entire project to the container
COPY . .
# Use Maven to build the project
RUN mvn clean package -DskipTests
RUN ls -la /app/target

# Second stage: run stage
FROM openjdk:22-jdk-slim
WORKDIR /app
# Copy the jar file generated in the build stage to the run stage
COPY --from=build /app/target/petropolis-0.0.1-SNAPSHOT.jar /app/petropolis.jar
# Expose the port that the application will run on
EXPOSE 8443
# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/petropolis.jar"]
