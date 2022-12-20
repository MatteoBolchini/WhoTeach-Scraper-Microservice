FROM openjdk:17
WORKDIR /app
COPY ./ ./
#RUN ./mvnw dependency:resolve
EXPOSE 8080
CMD ["java", "--enable-preview", "-jar", "target/WhoTeach-Scraper-Microservice-0.0.1.jar"]