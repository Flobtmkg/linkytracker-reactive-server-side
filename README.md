COMPILATION :

- A compilation phase is needed before launching manually or building the Docker image
- Requirements : JDK 17, Maven version 3.8+
- Command : mvn clean package
- The JAR Artifact can be found in /target/linkydataserver-*VERSION*.jar

DOCKER IMAGE BUILD :

- A Docker image can be built since the maven compilation phase happened and the /target/linkydataserver-*VERSION*.jar artifact has been generated
- Command : docker build -t linkydataserver:latest .

DOCKER RUN :

- Command to run the newly built image on default HTTP port 80 with a ./database folder to store the datas : docker run -p 127.0.0.1:80:8080 -p 127.0.0.1:8082:8082 -v ./database:/database linkydataserver:latest
