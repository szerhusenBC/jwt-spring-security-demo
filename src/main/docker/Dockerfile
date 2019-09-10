FROM adoptopenjdk/openjdk11:alpine
MAINTAINER Stephan Zerhusen <strephan.zerhusen@gmail.com>

# Add the service itself
ARG JAR_FILE
ADD target/${JAR_FILE} /usr/share/jwt-spring-security-demo.jar

ENTRYPOINT ["java", "-jar", "/usr/share/jwt-spring-security-demo.jar"]
