FROM azul/zulu-openjdk-alpine:11-jre
RUN mkdir /opt/app
COPY blocking_server.jar /opt/app
CMD ["java", "-jar","-XX:+UseSerialGC","-verbose:gc","-XX:+PrintGCDetails","/opt/app/blocking_server.jar"]
EXPOSE 9001