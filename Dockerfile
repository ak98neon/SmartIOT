ARG service_name=smart-iot
ARG version=1.0.0

FROM openjdk:8-jdk-alpine
USER root

WORKDIR /code
COPY ./target/iot-1.0.0.jar /code/target/iot-1.0.0.jar
COPY ./src/main/resources/application.yml /code/application.yml

EXPOSE 8080

CMD ["java", "-jar", "/code/target/iot-1.0.0.jar", "--spring.config.location=/code/application.yml"]