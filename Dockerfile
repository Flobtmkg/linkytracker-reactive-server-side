FROM eclipse-temurin:17

VOLUME /database

# Disable H2-Console
ENV SPRING_PROFILES_ACTIVE=prod

# Store data of the embeded RAM database inside a file on exit and auto reload it on init so it can be persisted
# We init the database at startup with an SQL script directly as an option of the datasource connection
ENV SPRING_R2DBC_URL=r2dbc:h2:file:////database;INIT=runscript%20from%20'classpath:init.sql';DB_CLOSE_ON_EXIT=FALSE;AUTO_RECONNECT=TRUE
ENV SERVER_PORT=8080
#ENV ALLOW_ORIGINS=http://localhost:80

COPY ./target/linkydataserver-*.jar /linkydataserver/linkydataserver.jar

ENTRYPOINT [ "java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "/linkydataserver/linkydataserver.jar" ]
