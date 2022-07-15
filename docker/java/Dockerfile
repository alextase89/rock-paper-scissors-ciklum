FROM maven:3.8.5-openjdk-17-slim as build
WORKDIR /home/app
COPY rock-paper-scissor-api/src src
COPY rock-paper-scissor-api/pom.xml .
RUN mvn clean package
ARG JAR_FILE=target/rock-paper-scissor-api*.jar

FROM bellsoft/liberica-openjre-alpine:17.0.3.1-2
COPY --from=build /home/app/target/rock-paper-scissor-api*.jar /app.jar
EXPOSE 8080
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]