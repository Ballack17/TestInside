FROM openjdk:17.0.1-jdk
ARG JAR_FILE=mainapp/test-inside.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar","/app.jar"]
