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
 
# Script SQL

Script necessary for the relational model.
 
     create table roles
     (
         id          int auto_increment,
         name        varchar(50)  not null,
         description varchar(255) null,
         constraint roles_pk
             primary key (id)
     ) engine = InnoDB,
       charset = UTF8;
     
     create table users
     (
         id       int auto_increment,
         username VARCHAR(255) not null,
         password varchar(255) not null,
         constraint users_pk
             primary key (id)
     ) engine = InnoDB,
       charset = UTF8;
     
     create unique index users_username_index
         on users (username);
     
     create table users_roles
     (
         id   int auto_increment,
         user int not null,
         role int not null,
         constraint users_roles_pk
             primary key (id),
         constraint users_roles_user_fk
             foreign key (user) references users (id),
         constraint users_roles_role_fk
             foreign key (role) references roles (id)
     ) engine = InnoDB,
       charset = UTF8;
     
     create index users_roles_user_index on users_roles (user);
     create index users_roles_role_index on users_roles (role);
     
# Testing script SQL

    insert into roles(name) value ('ADMIN');
    insert into roles(name) value ('USER');
    
    insert into users(username, password)
    values ('admin', '$2y$12$Ne9nOW.EFmf2AcOMzq8y1OIaUsPhwpmAreihmEes2BjXgtDS3scTW');
    insert into users(username, password)
    values ('user', '$2y$12$Ne9nOW.EFmf2AcOMzq8y1OIaUsPhwpmAreihmEes2BjXgtDS3scTW');
    
    insert into users_roles(user, role)
    values (1, 1);
    insert into users_roles(user, role)
    values (2, 2);