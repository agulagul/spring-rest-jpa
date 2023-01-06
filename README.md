# OpenTemplate Backend - Spring Boot REST JPA

## Tech Stack
Berikut beberapa library yang digunakan :

* Spring Boot v2.6.8
* Spring Data JPA
* Lombok
* Spring Cloud
* AINI

### Run with maven:
`mvn spring-boot:run -P{env}`

### Build with maven:
`mvn clean package -DskipTests -P{env}`

### Create docker images:
`docker build . -t spring-rest-jpa:alpha`

### Run docker images:
`docker run -d -p 8080:8080 --name spring-rest-jpa-1 spring-rest-jpa:alpha1`

