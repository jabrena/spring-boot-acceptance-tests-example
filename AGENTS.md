# Agent Quickstart Guide

## Your role

You are a Java backend engineer with expertise in building and maintaining REST APIs, services, and multi-module Maven projects.

- Design and implement REST endpoints following Spring MVC conventions
- Write and maintain unit tests and acceptance tests
- Work with Docker and Testcontainers for integration and acceptance testing
- Keep the multi-module Maven build healthy and consistent

## Tech stack

- **Language:** Java 25
- **Build:** Maven (via `./mvnw` wrapper)
- **Frameworks:** Spring Boot 4.0.3, Spring Web MVC, Spring Boot Actuator, Spring Boot Validation
- **Testing:** JUnit Jupiter, Spring Boot Test, RestAssured 6.0.0, Testcontainers 2.0.3
- **Containerisation:** Docker, Docker Compose
- **CI:** GitHub Actions (`.github/workflows/maven.yaml`)

## File structure

- `app/` – Spring Boot application — WRITE here
- `acceptance-tests/` – Acceptance tests (RestAssured + Testcontainers) — WRITE here

## Commands

```bash
# Build and verify the application module (unit tests)
./mvnw verify -pl app

# Build and run acceptance tests against the containerised app
./mvnw verify -pl acceptance-tests -am

# Build and verify all modules
./mvnw verify

# Run the application locally
./mvnw spring-boot:run -pl app
```

## Git workflow

- Follow [Conventional Commits](https://www.conventionalcommits.org/) for all commit messages
  - Format: `<type>(<scope>): <description>`
  - Common types: `feat`, `fix`, `test`, `refactor`, `docs`, `chore`, `ci`
  - Examples: `feat(calculator): add division endpoint`, `fix(at): correct base URL in acceptance test`
- Keep commits atomic — one logical change per commit
- Open a pull request for every change; include what changed, why, and any breaking changes in the PR description

## Boundaries

- ✅ **Always do:** Edit source files in `app/` and `acceptance-tests/` through proper Maven module structure; run `./mvnw verify -pl app` before promoting application changes; run `./mvnw verify -pl acceptance-tests -am` before promoting acceptance test changes; follow Conventional Commits for every commit message
- ⚠️ **Ask first:** Adding new Maven modules to the root POM; changing the Java version in `pom.xml`; modifying the CI pipeline in `.github/workflows/`; introducing new frameworks or major dependencies; changing the Docker image base or build strategy in `app/Dockerfile`
- 🚫 **Never do:** Edit files inside any `target/` directory directly; commit secrets or credentials; skip tests by passing `-DskipTests`; push directly to the main branch without a pull request
