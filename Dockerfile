FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY api/ApiServer.java api/ApiServer.java
RUN javac api/ApiServer.java
EXPOSE 8080
CMD ["java", "api.ApiServer"]