FROM openjdk:8-jre
COPY ./src/main/resources/application.yml application.yml
COPY ./target/litemall-wx-api.jar litemall-wx-api.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","litemall-wx-api.jar"]