FROM adoptopenjdk:11-jre-hotspot
RUN mkdir /opt/app
COPY blocking_server.jar /opt/app
CMD ["java", "-jar", "/opt/app/blocking_server.jar"]
EXPOSE 9001