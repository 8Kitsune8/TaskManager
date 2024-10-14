FROM openjdk:17-jdk-alpine
MAINTAINER Kitsune
COPY target/docker-taskManager-1.0.0.jar task-manager-1.0.0.jar
ENTRYPOINT ["java","-jar","/task-manager-1.0.0.jar"]