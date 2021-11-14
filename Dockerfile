FROM openjdk:15-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} storage-core.jar
ENTRYPOINT ["java", "-jar", "/storage-core.jar"]