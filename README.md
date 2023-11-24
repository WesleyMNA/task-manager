# Task Manager

Simple application inspired by Gitlab issue board for managing tasks.

## task-api

API build with functionalities to help people manage tasks.

### Dependencies

| Dependency  | Description          | Version |
|-------------|----------------------|---------|
| Java        | Programming language | 17      |
| Spring Boot | Framework            | 2.7.16  |
| PostgreSQL  | Database             | 15      |

### Functionalities

- Auth with JWT and Refresh Token;
- CRUDs of:
  - Clients;
  - Notes;
  - Tasks.
- Hateoas.

## task-ui

Simple front-end developed with VueJs to manage tasks via browser.

### Dependencies

| Dependency | Description          | Version |
|------------|----------------------|---------|
| Typescript | Programming language | 4.5.5   |
| VueJs      | Framework            | 3.2.13  |
| Pinia      | Store library        | 2.1.7   |
| Nginx      | Web server           | 1.25.3  |

## Running the project

1. Clone this repository and open it with an IDE as a Maven project, set up a PostgreSQL database, change the environment variable ``DB_HOST`` with the IP of the db and then run the method main inside TaskApiApplication class;

OR

2. If you have Docker installed, run the command ``docker compose up -d --build`` to run the API, Front-end and a database in containers.  

