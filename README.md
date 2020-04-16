## groups-management-application

This is a group management application built using springboot with spring security, OAuth2 and mysql DB for storing data. Client side code has been written using Reactjs. To containerize the application, a docker compose file has been created which will spin up three services:
1. mysql DB service: exposed on port 3306
2. springboot app-server service: expose on port 8080
3. react Js client-app service: exposed on port 3000.

Once all three services are up, three containers are started for three services and application can be accessed on :
http://localhost:3000

### Steps to build the application using docker-compose:

1. Clone this repository in local.
2. Change directory to the location of the repository.

To build images for app-server, client-app , db 
run: 
   `docker-compose build`

This step will download all maven dependencies for app-server. Once app-server is up, it will build client app and download all node modules. After this step three services will be started : will be created.

To start three containers 
run: 
   `docker-compose up`

Once all three containers are up, go to your browser and hit : http://localhost:3000

Signup using google or manual and start creating and managing groups.


### Steps to build the application locally with maven and npm:
1.	Clone this repository and change location to this repository.
2.	Install mysql db and start mysql
3.	Create a new db, db user and set its password or use root user. Update the same configuration in application.properties file of app-server folder.
4.	cd app-server
5.	Run `mvn clean install`. This should spin app-server on port 8080.
6.	cd client-app
7.	Run `npm install`
8.	Run `npm start`. This should spin client app on port 3000.
9.	Goto http://localhost:3000. Signup using google or manual and start creating and managing groups.


