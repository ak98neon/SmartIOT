ARG service_name=iot
ARG version=1.0.0

FROM openjdk:8-jdk-alpine
USER root

WORKDIR /code
COPY ./target/${service_name}.jar /code/target/${service_name}.jar
COPY ./src/main/resources/application.yml /code/application.yml

EXPOSE 8080

CMD ["java", "-jar", "/code/target/${service_name}.jar", "--spring.config.location=/code/application.yml"]