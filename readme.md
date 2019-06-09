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


#### Streaming 

From the command line add the reacitve stream operators extension.

    mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-smallrye-reactive-streams-operators"
    
    
Create a `StreamingGreetingService` and inject it into `Hello` resource.
Add a String array with greeing messages.
Create a method that returns a `Publisher<String>` and internally uses `Flowable.interval` to return a random greeing message at the specified interval.
Back to the `Hello` resource. Create a method like `hello` say `stream` that uses a differnt path, say `@Path("/stream")` and insted of `TEXT_PLAIN` return `SERVER_SENT_EVENTS`.
This method should now delegate to the injected `StreamingGreetingService`.

#### Use reactive postgres client.

From the command line add the reacitve stream operators extension.

    mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-reactive-pg-client"
    
Ensure that posgres is up an running (see  [Assets](#Assets). Describe the greetings table.

Create a `PgGreetingService` that injects a `io.reactiverse.axle.pgclient.PgPool`.
Create a method that runs a simple query like `selet * from greeting where lang='it'`.
From the result get the `greeting` column and return that. The method should return `CompletionStage<String>`.
As before inject the `PgGreetingService` into hello and create a different endpoint.
In the application.properties the following properties are required:

    quarkus.datasource.url=vertx-reactive:postgresql://:5432/demo
    quarkus.datasource.driver=org.postgresql.Driver
    quarkus.datasource.username=root
    quarkus.datasource.password=password 

#### Use hibernate ORM
NOTE: Hibernate ORM conflicts with reactive postgres, so start by removing that dependency and code.

Add `hibernate-orm` and `jdbc-postgres` extensions.

    mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-hibernate-orm"
    mvn quarkus:add-extension -Dextensions="io.quarkus:quarkus-jdbc-postgresql"
    
Create a greeing entity with `greeting` and `lang` fields.
Create a `JpaGreetingService` that injects the entity manager and performs a query by id.

    quarkus.datasource.url=jdbc:postgresql:demo
    quarkus.datasource.driver=org.postgresql.Driver
    quarkus.datasource.username=root
    quarkus.datasource.password=password

Finally, inject the `JpaGreetingService` into `Hello` and create a new endpoint to show how it works.
    

## Milestones
- [Hello World](https://github.com/iocanel/voxxed-athens-2019/tree/01-hello-world)
- [Hello World with Externalized Property](https://github.com/iocanel/voxxed-athens-2019/tree/02-hello-world-with-externalized-property)
- [Hello World with Reactive](https://github.com/iocanel/voxxed-athens-2019/tree/03-hello-world-with-reactive)
- [Hello World with Reactive Postgres](https://github.com/iocanel/voxxed-athens-2019/tree/04-hello-world-with-reactive-postgres)
- [Hello World with ORM](https://github.com/iocanel/voxxed-athens-2019/tree/05-hello-world-with-orm)
