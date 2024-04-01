COMPILATION :
-------------

    - A compilation phase is needed before launching manually or building the Docker image
    
    - Requirements : JDK 17, Maven version 3.8+
    
    - Command : mvn clean package
    
    - The JAR Artifact can be found in /target/linkydataserver-*VERSION*.jar

DOCKER IMAGE BUILD :
--------------------

    - A Docker image can be built since the maven compilation phase happened and the /target/linkydataserver-*VERSION*.jar artifact has been generated
    
    - As we need to create an image that can be used for testing and production on potentially different processor architectures (for exemple to run on a rasberry PI) we need to use a multi-arch builder. You can create one with the command "docker buildx create --name multi-arch-builder --use --bootstrap". A new builder called multi-arch-builder will be created and will be used as default. It will download and use moby/buildkit as a dependency in order to build multi-architectures images.
    
    - Command (that push to remote) : docker buildx build --push --platform linux/amd64,linux/arm64 --tag *yourepo*/linkydataserver:latest .

DOCKER RUN :
------------

    - Command to run the newly built image on default HTTP port 80 with a ./database folder to store the datas :
        docker run -p 127.0.0.1:80:8080 -p 127.0.0.1:8082:8082 -v ./database:/database *yourepo*/linkydataserver:latest
