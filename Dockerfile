FROM openjdk:26-ea-25-jdk

COPY target/*.jar api.jar

# docker build -t nsa-spring-app:latest .