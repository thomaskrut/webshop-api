FROM eclipse-temurin:17-jdk-jammy
VOLUME /tmp
EXPOSE 8080
ARG JAR_FILE=build/libs/webshop-api-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]