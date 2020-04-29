FROM hub.c.163.com/library/java:latest
LABEL maintainer="wangxinxing961129@gmail.com"
ENV projectName litemall-recommender
ENV version 0.1.0
VOLUME /tmp
ADD target/$projectName-$version.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]