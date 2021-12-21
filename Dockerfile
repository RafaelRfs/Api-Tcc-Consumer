FROM alpine:latest as base
RUN apk --update add openjdk11

ADD target/*.jar /usr/local/lib/app-tcc-consumer.jar
EXPOSE 8080
ENTRYPOINT [ "sh", "-c", "java -jar /usr/local/lib/app-tcc-consumer.jar" ]