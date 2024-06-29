# petropolis-backend

This is the backend of the project petropolis for course capstone.

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

`http://localhost:8080/swagger-ui/index.html`
