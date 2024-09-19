FROM openjdk:21-jdk-slim

WORKDIR /app

COPY sensor-api-0.0.1-SNAPSHOT.jar /app/sensor-api-0.0.1-SNAPSHOT.jar

ENV DATABASE_URL=your-database-url
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sensor-api-0.0.1-SNAPSHOT.jar"]