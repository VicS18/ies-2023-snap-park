# FROM maven:3.8.2-jdk-8 # for Java 8
FROM maven:3.8.5-openjdk-17

WORKDIR /backend
COPY . .
RUN mvn --fail-never clean install

# PRODUCTION
# CMD mvn spring-boot:run -Dspring-boot.run.jvmArguments="--add-opens java.base/java.lang=ALL-UNNAMED"

# JVM DEBUGGING - "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"
CMD mvn spring-boot:run -Dspring-boot.run.jvmArguments="--add-opens java.base/java.lang=ALL-UNNAMED -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=8000"