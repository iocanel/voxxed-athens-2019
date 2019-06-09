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


## Milestones

