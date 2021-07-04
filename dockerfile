#Build environment
#FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
FROM maven:3.6.3-jdk-11 AS MAVEN_BUILD
COPY pom.xml /build/
#ADD  config /build/config
COPY src /build/src
WORKDIR /build/
ENV PORT 8080
RUN mvn -B -f ./pom.xml clean package -DskipTests
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)
#Production packaging
FROM openjdk:11.0.4-jre-slim-buster

ARG DEPENDENCY=target/dependency
#COPY config/bootstrap.yml /app/config/bootstrap.yml
COPY --from=MAVEN_BUILD /build/target/BigQuery-0.0.1-SNAPSHOT.jar /BigQuery-0.0.1-SNAPSHOT.jar
RUN ls ./
ENTRYPOINT ["java", "-jar", "BigQuery-0.0.1-SNAPSHOT.jar"]