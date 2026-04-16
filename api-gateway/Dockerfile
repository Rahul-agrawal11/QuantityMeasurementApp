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

# -------- Stage 1: Build --------
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /app

# Copy Maven wrapper + config
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Fix permissions + line endings (handles Windows issues)
RUN chmod +x mvnw && sed -i 's/\r$//' mvnw

# Download dependencies (better caching)
RUN ./mvnw dependency:go-offline -q

# Copy source code
COPY src src

# Build jar
RUN ./mvnw clean package -DskipTests -q


# -------- Stage 2: Run --------
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy built jar
COPY --from=builder /app/target/*.jar app.jar

# Railway provides PORT dynamically
ENV PORT=8080

EXPOSE 8080

# Start application
ENTRYPOINT ["java", "-jar", "app.jar"]