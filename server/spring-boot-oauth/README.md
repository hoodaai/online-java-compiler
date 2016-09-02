# Java Applications Compiler

## Overview

Build Java Application Compiler which get input source code in the form of zip file, 
then uncompress it and compile. If source is maven base then it runs mvn test phase on it.
After compilation it returns the message to the client.
It also has a centralized logging server which stores application log. 

## Technologies used
   - Java
   - Spring Boot
   - Spring oAuth
   - Spring Security
   - H2 Database (MySql for production)
   - Flyway
   - AngularJS
   - Grunt
   - Bower
   - BootStrap

## Features

  - RESTful APIs using Spring Boot
  - Integrated with oAuth
  - Integrated with schema migration tool, Flyway
  - Single page application client developed using AngularJS

# Running

## Server
- You can run this project from the command-line in the root directory of project:


       ```
       $ gradle build
       ```

       ```
       $ java -jar build/libs/app-1.0-SNAPSHOT.jar
       ```


- After startup, your instance will be available on localhost, port 8080.


## Client

- To run client, use following command

       ```
       $ bower install & npm install 
       $ grunt serve
       ```

- After startup, your instance will be available on localhost, port 8080.

### Creating Database

 - You don't need to create database manually. This project uses flyway to create database schema automatically on startup.



# Application Flow

- After running application, your first step is to register your account.

```
http://localhost:9000/#/register
```



# Curl Commands

      - Register user
      
      curl -X POST --header "Content-Type: application/json" --header "Accept: text/plain" -d '{ "authorities": [ "ROLE_ADMIN"], "activated": "true", "login" : "admin", "password":"admin", "email":"v@v.com", "langKey":"en"  }' http://localhost:8080/api/register -v
      
      curl -X POST --header "Content-Type: application/json" --header "Accept: text/plain" -d '{ "authorities": [ "ROLE_USER"], "activated": "true", "login" : "varunksingh", "password":"password", "email":"v2@v.com", "langKey":"en"  }' http://localhost:8080/api/register -v
     
     
      - Get user details
      
      curl -X GET --header "Accept: application/json" --header "Authorization: Bearer e8e66a54-33c9-46e4-8126-b70dc09d6e66" "http://127.0.0.1:8080/api/users/admin"
      
   
      - check if user is Authenticated
     
      curl -X GET --header "Accept: application/json" --header "Authorization: Bearer 268b9de3-17b0-4840-8250-14ee066143f2" "http://127.0.0.1:8080/api/authenticate" -v
      

      -  Get oauth/token (for OAuth2 client id and secret id see application.yml)
   
       curl -X POST -vu unicornapp:mySecretOAuthSecret http://localhost:8080/oauth/token -H "Accept: application/json" -d "username=admin&password=admin&grant_type=password&scope=read&client_id=unicornapp&client_secret=mySecretOAuthSecret"
      
       curl -X POST  http://localhost:8080/oauth/token -H "Accept: application/json" -d "username=varunksingh&password=password&grant_type=password&scope=read write&client_id=unicornapp&client_secret=mySecretOAuthSecret"


## Installation

#### Installing node

        ```
        $ sudo apt-get install python-software-properties
        Then, do this:
        
        $ sudo add-apt-repository ppa:chris-lea/node.js
        $ sudo apt-get update
        $ sudo apt-get install nodejs
        Then, you have the latest version of node.js installed.
        
        $ ln -s /usr/bin/nodejs /usr/bin/node
        ```

#### Installing npm

        ```
        $ sudo apt-get install npm
        ```

#### Installing grunt globally

        ```
        $ npm install -g grunt-cli
        ```

#### Installing bower globally

        ```
        $ npm install -g bower
        ```


