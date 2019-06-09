# Voxxed Athens: Get your hands dirty with Quarkus

This repository contains all the code and assets that was written during the presentation.

## Table of contents
 - [Requirements](#Requirements): What you need before we start.
 - [Assets](#Assets): Scripts and manifests to setup services we are going to use.
 - [Walkthrough](#Walkthrough): The steps we are going to follow.
 - [Milestones](#Milestones): Links to snapshots/milestones of this presentation.

## Requirements
To use this material you need to have setup the following:

- Docker & Docker compose
- Java 1.8+
- Maven
- Graal (used 1.0.0-rc-16-grl)
- Kubernetes or Openshift

## Assets

### Postgres
SQL scripts for creating a greetings database.
Shell scripts for starting and initalizing the greetings database via docker.

To start the database:

    cd assets/postgres
    ./start.sh
    
    
### Kafka
Docker compose manifest for starting a zookeepr/kafka cluster on docker.

To start kafka just connect:

    cd assets/kafa
    docker-compose up

## Walkthrough

### Hello world
Let's create a simple Hello World web application!

Create a project from the shell using:

    mvn io.quarkus:quarkus-maven-plugin:0.16.1:create -DprojectGroupId=org.acme -DprojectArtifactId=hello-world -DprojectVersion=0.1-SNAPSHOT -Dendpoint=/hello -DclassName=org.acme.Hello

Open the project and find `Hello.java` to give a glimpse.
From the shell run the application in dev mode:

    mvn compile quarkus:dev

Perform changes to the greeting message and demostrate how dev mode performs changes live, with no rebuilding involved.
From this point on, either align unit tests, or toggle unit tests off `-DskipTests`.

Demonstrate native build.

    mvn clean package -Pnative
    
Run the native application.


    ./target/hello-world-0.1-SNAPSHOT-runner
    
While the application is still running, from another shell check memory consumption:

    ps ax -o pid,rss,command | numfmt --header --from-unit=1024 --to=iec --field 2 | grep -v grep | grep hello
    
At this point the result should be somewhere around `16M` to `19M`.

#### Fun with docker

To emphasize that native binaries need no jvm, change the base image of `Dockerfile.native` to use a minimal glibc based image.
`debian:jessie-slim` is a good example.

Build the image.

     docker build -f src/main/docker/Dockerfile.native -t iocanel/hello-world:0.1-SNAPSHOT .
     
Run the container.

    docker run -it -p 8080:8080 iocanel/hello-world:0.1-SNAPSHOT
    
Show that the application is running as expected.
    
#### Externalize Properties

Optinally, create a `GreetingService` and inject it into `Hello` resource.
Add a `@ConfigProperty(name="message")` annotation onto a String property and crate a method that returns that.
In the `Hello` resource, change the hello method so that it calls this method.
Open `application.properties` and add the value for `message`.


## Milestones
- [Hello World](https://github.com/iocanel/voxxed-athens-2019/tree/01-hello-world)
- [Hello World with Externalized Property](https://github.com/iocanel/voxxed-athens-2019/tree/02-hello-world-with-externalized-property)
