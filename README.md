# Java Spring Boot Employee Skills Management API

This project is a Spring Boot REST API for managing employees, their skills, and industry-related skill recommendations.  
It uses **MySQL** as the database and is designed to be deployed with **Docker** on Render.

---

## Features

- Add, update, and delete employees.
- Assign skills to employees.
- Get skill recommendations by industry.
- Manage skills and industry-specific mappings.
- RESTful API endpoints.
- MySQL database integration.

---

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **MySQL**
- **Maven**
- **Docker**
- **Render** (for deployment)

---

## Prerequisites

Before running the project, make sure you have:

- Java 17 installed
- Maven installed
- MySQL running locally or on a cloud service
- Docker (if you plan to containerize and deploy)

---

## Local Setup

1. **Clone the Repository**
   ```bash
   git clone https://github.com/<your-username>/<your-repo>.git
   cd <your-repo>
   ```

2. **Configure MySQL Connection**  
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
   spring.datasource.username=root
   spring.datasource.password=yourpassword
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   ```

3. **Build & Run Locally**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

4. **Access the API**
   - Base URL: `http://localhost:8080`
   - Example Endpoint: `GET /employees`

---

## Docker Deployment

### Dockerfile
```dockerfile
# Use Maven to build the application
FROM maven:3.9.4-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY . .
RUN mvn clean package -DskipTests

# Use JDK to run the application
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Build & Run Docker Image Locally
```bash
docker build -t employee-api .
docker run -p 8080:8080 employee-api
```

---

## Deploy to Render

1. Push your code (including `Dockerfile`) to GitHub.
2. Go to [Render](https://render.com) → **New Web Service**.
3. Connect your GitHub repo.
4. Select **Docker** as the environment.
5. Leave root directory empty (Dockerfile is in the root).
6. Click **Deploy**.

---

## API Endpoints

| Method | Endpoint                     | Description                      |
|--------|------------------------------|----------------------------------|
| GET    | `/employees`                  | Get all employees               |
| POST   | `/employees`                  | Create a new employee           |
| PUT    | `/employees/{id}`             | Update an employee              |
| DELETE | `/employees/{id}`             | Delete an employee              |
| GET    | `/skills`                     | Get all skills                  |
| POST   | `/skills`                     | Add a new skill                 |
| DELETE | `/skills/{id}`                | Delete a skill                  |
| GET    | `/recommendations/{industry}` | Get skill recommendations       |

---

## Environment Variables

When deploying to production, set these environment variables:

| Name                   | Example Value                         |
|------------------------|---------------------------------------|
| `SPRING_DATASOURCE_URL`      | `jdbc:mysql://host:3306/employee_db` |
| `SPRING_DATASOURCE_USERNAME` | `root`                            |
| `SPRING_DATASOURCE_PASSWORD` | `password`                        |

---

## License

This project is licensed under the MIT License.
