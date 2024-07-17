# petropolis-backend

This is the backend of the project petropolis for course capstone.

## Local Deployment

### Run MySQL via Docker

Run the following command to start a MySQL container:

`docker run --name mysql -e MYSQL_ROOT_PASSWORD=petropolis -d -p 3306:3306 mysql:latest`

### Run the application via Docker

Run the following command to start the application container:

`docker run -d -p 8443:8443 -e SPRING_DATASOURCE_URL="jdbc:mysql://host.docker.internal:3306/petropolis?createDatabaseIfNotExist=true&characterEncoding=utf-8" -v {path_to_your_image_folder}:/app/src/main/resources/tmp sherryu/petropolis`

### Access the API documentation at

`https://localhost:8443/swagger-ui/index.html`

## Development Guide

### Run MySQL via Docker

Run the following command to start a MySQL container:

`docker run --name mysql -e MYSQL_ROOT_PASSWORD=petropolis -d -p 3306:3306 mysql:latest`

A MySQL container will be started with 3306 port exposed mapped to the host machine. So you can access the MySQL server
directly from your host machine by running the following command:

`mysql -h 127.0.0.1 -P3306 -u root -ppetropolis`

If you need to exec into the MySQL container, you can simply run the following command:

`docker exec -it mysql bash`

We are using [hibernate](https://hibernate.org/) as ORM to generate the database schema, so we don't need to create the
database schema manually.

> If you do need to create the database schema manually, you can connect to the MySQL server and run the following
commands:
>
> -> exec petropolis.user.sql create user table
>
> -> exec petropolis.prompt.sql create prompt table

### Run application

`mvn spring-boot:run`

### Access the API documentation at

`https://localhost:8443/swagger-ui/index.html`
