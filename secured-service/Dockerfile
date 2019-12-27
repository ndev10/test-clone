FROM openjdk:8u131-jre-alpine
COPY ./target/secured-service-*.jar securedService.jar
CMD ["java", "-jar", "-Xmx256m", "securedService.jar"]
