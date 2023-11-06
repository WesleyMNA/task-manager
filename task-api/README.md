# Task Manager API

API build with functionalities to help people manage tasks inspired by Gitlab Board.

## Dependencies

| Dependency  | Version |
|-------------|---------|
| Java        | 17      |
| Spring Boot | 2.7.16  |

## Functionalities

- Auth with JWT and Refresh Token;
- CRUDs of:
  - Clients;
  - Notes;
  - Tasks.
- Hateoas.

## Environment Variables

### Auth

| Env              | Description                         |
|------------------|-------------------------------------|
| JWT_PUBLIC_KEY   | JWT's public key path               |
| JWT_PRIVATE_KEY  | JWT's private key path              |
| JWT_LIFETIME     | JWT's lifetime in minutes           |
| REFRESH_LIFETIME | Refresh token's lifetime in minutes |

### Database

| Env         | Description               |
|-------------|---------------------------|
| DB_HOST     | Postgres' host            |
| DB_USER     | Postgres' user            |
| DB_PASSWORD | Postgres' user's password |
| DB_NAME     | Postgres' name            |

### Utils

| Env       | Description     |
|-----------|-----------------|
| TIMEZONE  | API's timezone  |
| LOG_LEVEL | API's log level |

## Running the project

1. Clone this repository and open it with an IDE as a Maven project, set up a PostgreSQL database to be used to store
   data,
   change the environment variable ``DB_HOST`` with the IP of the db and then run the method main inside
   TaskApiApplication class;

OR

2. If you have Docker installed, run the command ``docker compose up -d --build`` to run the API and a database in
   containers.  

