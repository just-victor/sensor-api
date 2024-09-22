FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/sensor-api-0.0.1-SNAPSHOT.jar /app/sensor-api-0.0.1-SNAPSHOT.jar

ENV DATABASE_URL="mongodb+srv://sensor:QssdA2Lp7W9t7B2b@cluster0.vxtne.mongodb.net/sensor-db?retryWrites=true&w=majority&appName=Cluster0"
ENV SERVER_PORT=8080

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "sensor-api-0.0.1-SNAPSHOT.jar"]