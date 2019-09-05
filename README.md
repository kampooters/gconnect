# gconnect (Global Payment Gateway Integration)
Global Payment Gateway Integration is project to provide the payment gateway integration

## Getting Started
### About
Global Payment Gateway Integration is project to provide the payment gateway integration. It provides the 
following functionalities
##### Multiple Payment gateway integration
##### REST APIs
###### oAuth2 based Authentication
  * Register user with client-id and secret
  * Get Access Token
  * Refresh Access Token

     
     
### Architecture and Design
* Architecture: 

###Technical Dependencies
The code for this application is written 
* Java 8
* Sofa Boot
* Microservices Architecture pattern
* Mysql
* Mybatis
* Logback
* SL4j
* oAuth2 (Application mode) RFC specifications
* Message Broker (RabbitMQ) AMQ Spring client
* Maven

### Prerequisites

Following software need to be installed for development and debugging purpose.

```
JDK 1.8 for development or changes in code.
IDE(IntelliJ IDEA or Eclipse etc) is required for setting the code.
MYSQL server is required for Database.
MYSQL client like mysql workbench which can help to see database entries.
maven is used for dependency management.

```

### Installation

* [JDK](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html?printOnly=1) - The Java Development Kit provides java libraries for development
* [IntelliJ IDEA](https://www.jetbrains.com/idea/) - The IDE for code changes and Development Kit used
* [Mysql Server and MYSQL client](https://www.mysql.com/downloads/) - These are used to connect this application with Database
* [Maven] - JDK is prerequisites for maven installtion
* Git


### Environment Variables

After installing JDK and Gradle you need to set Environment Variable like

```
JAVA_HOME = C:\Program Files\Java\jdk1.8.0_212
MAVEN_HOME = <maven installation directory>
```

### Install Rabbit MQ on Windows locally
* Install erlang
* Downlad rabbitmq server
* intall rabbitmq server and run as service
* default credentials are guest/guest
* login to admin in browser http://localhost:15672/


### Documentation

#### Requirements docs
go to <root>/docs/requirements

#### Architecture design docs
go to <root>/docs/architecture-design

#### API docs
* http://localhost:<port>/swagger-ui.html

#### Java docs (Source code)
* go to <root>/docs/java-docs
* If you want to generate new one got to menu->Tools -> Generate Javadocs and save to <root>/docs/java-docs




### Deployment


#### Local setup
* import the git repo <http://..../gconnect.git>
* checkout dev branch
* Import project in intellij idea
* Read the README.md file
* Run schema file under gconnect/schema/gconnect.schema on mysql
* Change your credentials in dev-local.properties branch for rabbitmq and mysql
* Build the project
* Run ``` mvn clean install```
* Run or debug the project and application will start on port provided in application.properties file
* Access swagger on http://localhost:<port>/swagger-ui.html


#### Prod/Staging deployment
* import the git repo <http://1uri..../gconnect.git>
* checkout dev branch
* Import project in intellij idea
* Read the README.md file
* Run schema file under gconnect/schema/gconnect.schema on mysql
* Change your credentials in application-prod.properties branch for rabbitmq and mysql and others if any
* Build the project
* Run ``` mvn clean install```
* Run ``` mvn clean package```
* .jar file will be created
* Make ssh connection to your prod machine
* Move the jar to prod machine
* Run command on prod machine linux ``` service <service-name> stop```
* Run command on prod machine linux ``` service <service-name> start```
* To view the logs go to /logs folder and run command ``` journalctl -f -u <service-name>```
* Run commands to edit -prod.properties file ``` vim <jar name>``` write changes to file, quit and restart the application
* Access swagger on http://<ip>:<port>/swagger-ui.html



## Versioning

We use our local GitLab instance which is accessable only from our office network. 

## Authors

* Abdulrehman (abdulrehman.abdul.qau@gmail.com)** - *Initial work* 

## License

This project is not free
