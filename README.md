# groups-management-application
This is a group management application built using springboot with spring security, OAuth2 and mysql DB for storing data. Client side code has been written using Reactjs. To containerize the application, a docker compose file has been created which will spin up three services:
1. mysql DB service: exposed on port 3306
2. springboot app-server service: expose on port 8080
3. react Js client-app service: exposed on port 3000.

Once all three services are up, three containers are started for three services and application can be accessed on :
http://localhost:3000

Steps to build the application using docker:

1. Clone this repository in local.
2. Change directory to the location of the repository.

To build images for app-server, client-app , db run:
    docker-compose build

This step will download all maven dependencies for app-server. Once app-server is up, it will build client app and download all node modules. After this step three services will be started : will be created.

To start three containers run:
    docker-compose up

Once all three containers are up, go to your browser and hit : http://localhost:8080

