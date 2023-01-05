# FROM maven:3.8.2-jdk-8 AS build_java
# ENV HOME=/app
# RUN mkdir -p $HOME
# WORKDIR $HOME
# COPY settings.xml /root/.m2/settings.xml
# COPY pom.xml $HOME
# RUN ["mvn", "dependency:go-offline", "-DskipTests"]

# COPY src ./src
# RUN ["mvn", "package", "-DskipTests"]

FROM openjdk:8
COPY src/main/resources/spring-rest-jpa-application.yml /etc/spring-rest-jpa-application.yml
# COPY --from=build_java /app/target/spring-rest-jpa.war /usr/local/lib/spring-rest-jpa.war
COPY target/spring-rest-jpa.war /usr/local/lib/spring-rest-jpa.war

EXPOSE 8088
ENTRYPOINT ["java", "-DCONFIG_DIR=/etc", "-jar", "/usr/local/lib/spring-rest-jpa.war"]