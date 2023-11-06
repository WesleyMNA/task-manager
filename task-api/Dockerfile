FROM maven:3.8.6-amazoncorretto-17 as builder

WORKDIR /app

COPY ./ /app

RUN mvn -DskipTests=true clean
RUN mvn -B -DskipTests install

RUN mkdir /target
RUN cp /app/target/*.jar /target


FROM openjdk:17-bullseye

ENV JAVA_OPTS ""
ENV PROJECT_NAME task-api
ENV TZ America/Sao_Paulo
ENV TIMEZONE America/Sao_Paulo

WORKDIR /tmp

RUN mkdir /opt/${PROJECT_NAME}
WORKDIR /opt/${PROJECT_NAME}

RUN apt-get install -y tzdata && \
    ln -sf /bin/bash /bin/sh

COPY --from=builder /target/ .

ENTRYPOINT java ${JAVA_OPTS} -jar $(ls *.jar)
