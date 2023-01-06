FROM maven:3.8.2-jdk-8-slim
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
COPY settings.xml /root/.m2/settings.xml
COPY pom.xml $HOME

COPY src ./src
RUN ["mvn", "package", "-DskipTests"]

COPY src/main/resources/spring-rest-jpa-application.yml /etc/spring-rest-jpa-application.yml

ENTRYPOINT ["java", "-DCONFIG_DIR=/etc", "-jar", "/app/target/spring-rest-jpa.jar"]