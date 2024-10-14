FROM openjdk:17-jdk-alpine
MAINTAINER Kitsune
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} task-manager-1.0.0.jar
ENTRYPOINT ["java","-jar","/task-manager-1.0.0.jar"]