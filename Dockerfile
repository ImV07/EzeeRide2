# 1️⃣ Build Stage
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
RUN chmod +x mvnw

COPY pom.xml .
COPY src ./src

RUN ./mvnw clean package -DskipTests

# 2️⃣ Run Stage
FROM eclipse-temurin:17-jdk
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
