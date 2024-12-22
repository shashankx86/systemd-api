# Systemd API

A RESTful API service to interact with systemd on Linux systems. Built with Spring Boot.

## Requirements

- Java 17 or higher
- Linux system with systemd
- Gradle (or use included wrapper)

## Setup

1. Clone the repository
2. Create a `.env` file in the project root with the following variables:
```properties
JWT_SECRET=your_secure_secret_key
ADMIN_USERNAME=your_admin_username
ADMIN_PASSWORD=your_admin_password
JWT_EXPIRATION=86400000
```

3. Build the project:
```sh
./gradlew build -x test
```

4. Run the application:
```sh
java -jar build/libs/systemd-api-0.0.1-a1.jar
```

## Features

- JWT-based authentication
- Systemd version information endpoints
- Support for both sudo and non-sudo operations
- Environment-based configuration

## API Endpoints

- `/api/auth` - Authentication endpoints
- `/api/user/version` - Get systemd version (non-sudo)
- `/api/sudo/version` - Get systemd version (with sudo)

## Security

- JWT token-based authentication
- Configurable admin credentials
- Sudo operation support with proper privilege handling
