# Stage 1: сборка
FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY pom.xml .
RUN mvn -q dependency:go-offline
COPY src ./src
RUN mvn -q package -DskipTests

# Stage 2: запуск
FROM eclipse-temurin:17-jre AS runtime
WORKDIR /app
COPY --from=builder /build/target/app.jar app.jar
EXPOSE 8080
CMD ["java", "-jar", "app.jar"]