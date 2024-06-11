# petropolis-backend

This is the backend of the project petropolis for course capstone.

## Development Guide

1 **Run MySQL via Docker:**
   
   `docker run --name mysql -e MYSQL_ROOT_PASSWORD=petropolis -d -p 3306:3306 mysql:latest`
  
2 **MySQL initialize**

   step 1 : get MySQL docker container id

   `docker container ls | grep mysql:latest | awk '{print $1}'`

   step 2 : open docker container bash
   
   `docker container exec -it {container id} /bin/bash`

   or

   `docker container exec -it $(docker container ls | grep mysql:latest | awk '{print $1}') /bin/bash`
   
   step 3 : accept MySQL

   `mysql -h 127.0.0.1 -P3306 -u root -ppetropolis`

   step 4 ： MySQL initialize

   -> exec petropolis.user.sql create user table

3 **Run application:**

   `mvn spring-boot:run`

4 **Access the API documentation at:**

   `http://localhost:8080/swagger-ui/index.html`
