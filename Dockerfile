FROM openjdk:17.0.1-jdk
ARG JAR_FILE=build/libs/test-inside.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8187
ENTRYPOINT ["java", "-jar","/app.jar"]
