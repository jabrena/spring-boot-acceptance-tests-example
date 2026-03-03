# Developer commands

## Essential maven commands

```bash
# Analyze dependencies
./mvnw dependency:tree
./mvnw dependency:analyze
./mvnw dependency:resolve

./mvnw clean validate -U
./mvnw buildplan:list-plugin
./mvnw buildplan:list-phase
./mvnw help:all-profiles
./mvnw help:active-profiles
./mvnw license:third-party-report

# Clean the project
./mvnw clean

# Run unit tests
./mvnw clean test

# Run integration tests
./mvnw clean verify

# Clean and package in one command
./mvnw clean package

# Check for dependency updates
./mvnw versions:display-property-updates
./mvnw versions:display-dependency-updates
./mvnw versions:display-plugin-updates

# Generate project reports
./mvnw site
jwebserver -p 8005 -d "$(pwd)/target/site/"
```

## Plugin Goals Reference

The following sections list useful goals for each plugin configured in this project's pom.xml.

### maven-enforcer-plugin

| Goal | Description |
|------|-------------|
| `./mvnw enforcer:enforce` | Execute enforcer rules |
| `./mvnw enforcer:display-info` | Display current platform information |

### maven-compiler-plugin

| Goal | Description |
|------|-------------|
| `./mvnw compiler:compile` | Compile main source files |
| `./mvnw compiler:testCompile` | Compile test source files |

### spring-boot-maven-plugin

| Goal | Description |
|------|-------------|
| `./mvnw spring-boot:run` | Run the application |
| `./mvnw spring-boot:build-image` | Build an OCI container image |
| `./mvnw spring-boot:repackage` | Repackage JAR/WAR as executable |
| `./mvnw spring-boot:build-info` | Generate build-info.properties |

### maven-failsafe-plugin

| Goal | Description |
|------|-------------|
| `./mvnw failsafe:integration-test` | Run integration tests |
| `./mvnw failsafe:verify` | Verify integration test results |

### maven-surefire-plugin

| Goal | Description |
|------|-------------|
| `./mvnw surefire:test` | Run unit tests |
| `./mvnw surefire:help` | Display help information |

## Submodules

This is a multi-module project. The following modules are declared in the root `pom.xml`.

| Module | Artifact ID | Commands | Description |
|--------|-------------|----------|-------------|
| app | app | `./mvnw clean verify -pl app`<br>`./mvnw clean install -pl app`<br>`./mvnw spring-boot:run -pl app` | Spring Boot application module with web MVC, actuator, and validation |
| acceptance-tests | acceptance-tests | `./mvnw clean verify -pl acceptance-tests -am` | Acceptance tests module using RestAssured and Testcontainers |