FROM openjdk:8u131-jre-alpine
COPY ./target/gateway-*.jar gateway.jar
CMD ["java", "-jar", "-Xmx256m", "gateway.jar"]
