FROM eclipse-temurin:17

VOLUME /database

# Disable H2-Console
ENV SPRING_PROFILES_ACTIVE=prod
# ENV SPRING_R2DBC_USERNAME=admin
# ENV SPRING_R2DBC_PASSWORD=password

# Store data of the embeded RAM database inside a file on exit and auto reload it on init so it can be persisted
ENV SPRING_R2DBC_URL=r2dbc:h2:file:////database;DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
ENV SERVER_PORT=8080
ENV ALLOW_ORIGINS=http://localhost:80

COPY ./target/linkydataserver-*.jar /linkydataserver/linkydataserver.jar

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/linkydataserver/linkydataserver.jar" ]
