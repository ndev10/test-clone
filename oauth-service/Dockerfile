FROM openjdk:8u131-jre-alpine
COPY ./target/oauth-service-*.jar oauthService.jar
CMD ["java", "-jar", "-Xmx256m", "oauthService.jar"]
