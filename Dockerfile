FROM gradle:6.7-jdk11-hotspot as build
WORKDIR /workspace/app

COPY src src
COPY build.gradle.kts .
COPY settings.gradle.kts .

RUN gradle :build

FROM openjdk:11-jre-slim-buster

RUN addgroup --system c2g && adduser --system c2g --ingroup c2g
USER c2g:c2g

ARG JAR_FILE=/workspace/app/build/libs/*.jar
COPY --from=build ${JAR_FILE} /app.jar
EXPOSE 8080
VOLUME /tmp

ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]
