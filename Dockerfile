FROM openjdk:21-jdk-slim

ARG JAR_FILE=target/bendis-seller-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

COPY src/main/resources/application.yaml application.yaml

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.config.location=classpath:/application.yaml"]
