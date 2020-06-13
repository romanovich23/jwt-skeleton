# JWT Skeleton

REST API project with Spring Boot, Spring Security, Spring Data JPA and JWT preconfigured.
> **Note:** This project uses the **MySQL driver**

# Required entities

This project needs two entities primarily.

| Entity | Fields |
|--|--|
| User | id, username, password, roles |
| Rol | id, name, description

# HTTP Request

All HTTP requests must contain an authorization header with the token received at the login.

# Token

The token is obtaining by logging in through the authorization header.

# Main methods

 - Save user
 - Delete user
 - Login
 - Test