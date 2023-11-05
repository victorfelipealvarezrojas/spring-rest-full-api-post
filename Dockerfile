FROM eclipse-temurin:17

LABEL maintainer = "victorfelipealvarezrojas@hotmail.com"

WORKDIR /app

COPY target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

EXPOSE 8080