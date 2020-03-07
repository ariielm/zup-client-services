FROM openjdk:8-jre-alpine

COPY target/zup-client-services-*.jar zup-client-services.jar

ENTRYPOINT exec java -jar -Dspring.profiles.active=production zup-client-services.jar