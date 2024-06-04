# petropolis-backend

This is the backend of the project petropolis for course capstone.

## Development Guide:

1. **Run MySQL via Docker:**
   
   `docker run --name mysql -e MYSQL_ROOT_PASSWORD=petropolis -d -p 3306:3306 mysql:latest`
   
2. **Run application:**

   `mvn spring-boot:run`

3. **Access the API documentation at:**

   `http://localhost:8080/swagger-ui/index.html`
