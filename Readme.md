# Note Taker Application

A simple note-taking application built with Spring Boot.

![Team Members](./image-reference/Team%20Members.png)

## Table of Contents
    - [About](#about)
    - [Getting Started](#getting-started)
    - [Source Code and Testing](#source-code-testing)
    - [Contact](#contact)

## About

The Note Taker App is a web application designed to allow users to create, read, update, and delete notes securely. It includes user authentication and authorization mechanisms to protect user data and ensure privacy.

### Features

    - Authentication and Authorization : Register and Login to get JWT Token in order to use API Resources.
    - CRUD Operation : Create , Read , Update and Delete notes securely.
    - Search Functionaltity : Search for Specific notes.

### Tech Stack

    - Backend              : JAVA with Spring Boot Framework
    - Package Manager      : Maven
    - Frontend/Client Tool : Postman 
    - Database             : PostgreSQL Server
    - DBMS                 : PgAdmin
    - Security             : JWT For authentication and built-in Spring Security
    - Encrypt Algorithm    : Bcrypt
    - ORM                  : JPA (Java Persistence API)

## Getting Started

To get a copy from this project , follow these simple step : 

    - Open your teminal : 
        ```bash
        git clone https://github.com/Th1234wee/note-taker-app.git
    - Install Pgadmin for DBMS
    - Set up Database like create database name <note-taker-database>
    - Update file <application.properties> into your credentials (environment variable or database credential)
    - change directory to your project
    - Run : mvn spring-boot:run

### API Endpoint Resources :

    - User Register     : /api/user/createUser
    - User Login        : /api/user/login
    - Get All Users     : /api/user/getUsers
    - Get User by Id    : /api/usergetUserByID/{id}
    - Edit User         : /api/user/editUser/{id}
    - Remove User       : /api/user/removeUser/{id}
    - Get Notes         : /api/note/getNotes
    - Get Notes By Id   : /api/note/getNoteByID/{id}
    ...

## Source Code and Testing
    - Link to Github Repository : https://github.com/Th1234wee/note-taker-app
    - Link to Postman           : https://app.getpostman.com/join-team?invite_code=18fb080a817b380e305a93cebdef4379&target_code=a2da8d930c7cec33a8ed9b64ca8d70bb

## Contact

For questions and feedbacks , feel free to reach out:

    - Gmail : theara6574@gmail.com
 

