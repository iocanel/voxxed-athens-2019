(setq idee-maven-project-settings-commands nil)(push "mvn clean package -Dnative=true -Dnative-image.docker-build=true" idee-maven-project-settings-commands)(push "mvn quarkus:dev" idee-maven-project-settings-commands)
