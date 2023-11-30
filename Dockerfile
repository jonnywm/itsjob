FROM alpine:latest

CMD ["/bin/sh"]

FROM openjdk:11-jre-slim
WORKDIR /app
COPY target/ist-job-1.0.jar /app/ist-job.jar
EXPOSE 9090
CMD ["java", "-jar", "app.jar"]
