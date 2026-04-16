## Stage 1: Build
#FROM eclipse-temurin:17-jdk-alpine AS builder
#WORKDIR /app
#
#COPY .mvn/ .mvn/
#COPY mvnw pom.xml ./
#
## ✅ THIS LINE IS REQUIRED
#RUN chmod +x mvnw
#
## now it will work
#RUN ./mvnw dependency:go-offline -q
#
#COPY src/ src/
#RUN ./mvnw clean package -DskipTests -q
#
## Stage 2: Run
#FROM eclipse-temurin:17-jre-alpine
#WORKDIR /app
#COPY --from=builder /app/target/*.jar app.jar
#EXPOSE 8080
#ENTRYPOINT ["java", "-jar", "app.jar"]

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY . .
RUN apk add --no-cache maven
RUN mvn clean package -DskipTests
RUN cp target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]