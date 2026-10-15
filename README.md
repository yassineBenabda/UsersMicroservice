# 👤 Users Microservice – Authentication & Registration

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.5-6DB33F?logo=springboot)
![Spring Security](https://img.shields.io/badge/Spring%20Security-JWT-6DB33F?logo=springsecurity)
![MySQL](https://img.shields.io/badge/MySQL-8-4479A1?logo=mysql&logoColor=white)

A Spring Boot microservice that handles **user registration, email verification and JWT authentication**. New users receive a 6-digit verification code by email and must confirm it before they can log in. On successful login, the service issues a signed **JWT** containing the user's roles.

It is used by the [Games Management front-end](https://github.com/yassineBenabda/MINI-PROJET-GAME-FrontEnd-) (Angular, `http://localhost:4200`).

---

## ✨ Features

- User registration with BCrypt password hashing
- Email verification with a 6-digit code (valid for **15 minutes**), sent via Gmail SMTP
- Accounts are **disabled** until the email is verified
- JWT login (HMAC256) with roles embedded in the token, valid for **10 days**
- Role management (`ADMIN`, `USER`) — new users get `USER` by default
- Centralized error handling with clear error codes
- CORS configured for the Angular client

---

## 🧰 Tech Stack

| Layer | Technology |
|---|---|
| Language | Java 21 |
| Framework | Spring Boot 3.3.5 |
| Security | Spring Security, `com.auth0:java-jwt` |
| Persistence | Spring Data JPA / Hibernate |
| Database | MySQL |
| Mail | Spring Boot Starter Mail (Gmail SMTP) |
| Utilities | Lombok, DevTools |
| Build | Maven (wrapper included) |

---

## 📁 Project Structure

```
src/main/java/com/yassine/users
├── entities/              # User, Role
├── repos/                 # UserRepository, RoleRepository
├── restControllers/       # UserRestController
├── security/              # SecurityConfig, JWT filters, UserDetailsService, SecParams
├── service/
│   ├── register/          # RegistrationRequest, VerificationToken (+ repository)
│   ├── exceptions/        # Custom exceptions + GlobalExceptionHandler
│   ├── UserService.java
│   └── UserServiceImpl.java
├── util/                  # EmailSender, EmailService
└── UsersMicroserviceApplication.java
```

---

## 🗂️ Data Model

```
User * ──── * Role          (join table: user_role)
User 1 ──── 1 VerificationToken
```

| Entity | Fields |
|---|---|
| **User** | `user_id`, `username` (unique), `password`, `email`, `enabled`, `roles` |
| **Role** | `role_id`, `role` |
| **VerificationToken** | `id`, `token`, `expirationTime`, `user` |

---

## ⚙️ Prerequisites

- JDK 21
- MySQL 8 on `localhost:3306`
- A Gmail account with an **App Password** (for sending verification emails)

---

## 🚀 Getting Started

### 1. Clone the repository

```bash
git clone https://github.com/yassineBenabda/UsersMicroservice.git
cd UsersMicroservice
```

### 2. Configure `application.properties`

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/users_db?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update

server.port=8081
server.servlet.context-path=/users

spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=<your-email>@gmail.com
spring.mail.password=<your-gmail-app-password>
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true
```

> ⚠️ Never commit your real email password. Use a Gmail App Password and keep it out of version control (environment variables or a local, git-ignored config file).

### 3. Seed the roles

Registration assigns the `USER` role, so the roles must exist in the database. After the first start (tables are created automatically), run:

```sql
INSERT INTO role (role) VALUES ('ADMIN'), ('USER');
```

### 4. Run the application

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows
mvnw.cmd spring-boot:run
```

The service is available at **`http://localhost:8081/users`**.

---

## 📡 API Endpoints

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| POST | `/users/register` | Public | Register a new user and send a verification code by email |
| GET | `/users/verifyEmail/{code}` | Public | Verify the email with the 6-digit code and enable the account |
| POST | `/users/login` | Public | Authenticate and receive a JWT in the `Authorization` response header |
| GET | `/users/all` | Authenticated | List all users |

### Registration flow

```
POST /register ──▶ user saved (enabled = false) ──▶ code emailed
       │
       ▼
GET /verifyEmail/{code} ──▶ user enabled ──▶ POST /login ──▶ JWT
```

### Examples

**Register**

```bash
curl -X POST http://localhost:8081/users/register \
  -H "Content-Type: application/json" \
  -d '{"username": "yassine", "email": "yassine@example.com", "password": "secret123"}'
```

**Verify email**

```bash
curl http://localhost:8081/users/verifyEmail/123456
```

**Login**

```bash
curl -i -X POST http://localhost:8081/users/login \
  -H "Content-Type: application/json" \
  -d '{"username": "yassine", "password": "secret123"}'
```

The JWT is returned in the `Authorization` response header. Its payload looks like:

```json
{
  "sub": "yassine",
  "roles": ["USER"],
  "exp": 1760000000
}
```

---

## ❗ Error Handling

Errors are returned as JSON by `GlobalExceptionHandler`:

```json
{
  "timestamp": "2026-10-15T10:30:00",
  "message": "Email déjà existant!",
  "path": "uri=/users/register",
  "errorCode": "USER_EMAIL_ALREADY_EXISTS"
}
```

| Error code | HTTP status | When |
|---|---|---|
| `USER_EMAIL_ALREADY_EXISTS` | 400 | Registering with an email already in use |
| `EXPIRED_TOKEN` | 400 | Verification code older than 15 minutes |
| `INVALID_TOKEN` | 404 | Verification code not found |
| `disabled` | 403 | Login attempt before the email is verified |

---

## 🖥️ Front-end

CORS is configured to allow requests from `http://localhost:4200`, and the `Authorization` header is exposed so the Angular client can read the token after login.

---

## 👤 Author

**Yassine Benabda** — [GitHub](https://github.com/yassineBenabda)
