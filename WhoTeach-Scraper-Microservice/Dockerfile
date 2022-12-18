FROM openjdk:17-alpine

# https://blog.phusion.nl/2015/01/20/docker-and-the-pid-1-zombie-reaping-problem/
RUN apk add dumb-init
RUN mkdir -p /opt/app/data

COPY target/*.jar /opt/app/app.jar

RUN addgroup --system javauser && adduser -S -s /bin/false -G javauser javauser
RUN chown -R javauser:javauser /opt/app
USER javauser
WORKDIR /opt/app
EXPOSE 8080
CMD "dumb-init" "java" "-jar" "app.jar"