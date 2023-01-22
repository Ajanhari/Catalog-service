# Ubuntu base image with the Eclipse Temurin distribution of the JRE pre-installed
FROM eclipse-temurin:17 AS builder
#Changes the current working directory to “workspace”
WORKDIR workspace
# Builds argument specifying the location of the application JAR file in your project
ARG JAR_FILE=build/libs/*.jar
#Copies the application JAR file from the local machine into the image
COPY ${JAR_FILE} catalog-service.jar
RUN java -Djarmode=layertools -jar catalog-service.jar extract

FROM eclipse-temurin:17
WORKDIR workspace
RUN useradd spring
USER spring
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
# Sets the container entry point to run the application
#ENTRYPOINT ["java", "-jar", "catalog-service.jar"]