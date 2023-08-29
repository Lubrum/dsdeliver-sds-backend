FROM eclipse-temurin:20.0.2_9-jre-alpine@sha256:987af6a7e762cf950598c8f5ebaad9fff9dcf220fc8a3bddb9fd55b49b48a3e4

WORKDIR /app
RUN echo http://dl-2.alpinelinux.org/alpine/edge/community/ >> /etc/apk/repositories && \
    apk --no-cache add shadow && \
    apk --no-cache add zip && \
    addgroup -S spring &&  \
    adduser -S spring -G spring && \
    usermod -a -G spring root && \
    mkdir /app/lib && \
    mkdir /app/META-INF

COPY ./target/dsdeliver-*.jar /app/jar/

RUN unzip /app/jar/dsdeliver-*.jar -d /app/jar/ && \
    pwd && ls -lht /app/jar && find && \
    mv -v /app/jar/BOOT-INF/lib/* /app/lib && \
    mv -v /app/jar/META-INF/* /app/META-INF && \
    mv -v /app/jar/BOOT-INF/classes/* /app && \
    rm -rf /app/jar

RUN chown -R spring:spring /app

USER spring:spring
ENTRYPOINT ["java","-Dspring.profiles.active=prod", "-cp","/app:/app/lib/*","com.devsuperior.dsdeliver.DsdeliverApplication"]
EXPOSE 8200
