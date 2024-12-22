# Usage Guide

## Environment Setup

1. Create a `.env` file in the project root with required configuration:

```properties
JWT_SECRET=your_secure_secret_key
ADMIN_USERNAME=your_admin_username
ADMIN_PASSWORD=your_admin_password
JWT_EXPIRATION=86400000
```

## Building

Build the project using the Gradle wrapper:

```sh
./gradlew build -x test
```

The built JAR will be in `build/libs/`

## Running

1. Start the application with the environment file path:
```sh
java -jar -Denv.path=./.env systemd-api-0.0.1-a1.jar
```

Note: The `-Denv.path` flag is required to specify the location of your .env file. If not provided, the application will look for the .env file in the current directory.

2. The API will be available at `http://localhost:5470/api`

## Authentication

1. Get a JWT token by authenticating with your admin credentials:
```sh
curl -X POST http://localhost:5470/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"your_admin_username","password":"your_admin_password"}'
```

2. Use the returned token in subsequent requests:
```sh
curl http://localhost:5470/api/user/version \
  -H "Authorization: Bearer your_jwt_token"
```

## API Endpoints

### Version Information
- GET `/api/user/version` - Get systemd version (non-sudo)
- GET `/api/sudo/version` - Get systemd version (with sudo)

## Configuration Options

The application can be configured through:
- Environment variables
- `.env` file
- `application.properties`

### Available Properties
- `server.port` - API port (default: 5470)
- `spring.application.environment` - Application environment
- `app.security.sudo.enabled` - Enable/disable sudo operations
