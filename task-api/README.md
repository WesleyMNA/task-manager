# Task API

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

