FROM openjdk:8-alpine

COPY target/uberjar/telepathic.jar /telepathic/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/telepathic/app.jar"]
