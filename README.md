# Spring-boot Acceptance tests example

## How to run in local?

```bash
./mvnw verify -pl app
./mvnw verify -pl acceptance-tests -am
```

## MCP Servers

### maven-tools-mcp

```json
{
  "mcpServers": {
    "maven-tools": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "arvindand/maven-tools-mcp:latest"
      ]
    },
    "javadocs": {
      "url": "https://www.javadocs.dev/mcp"
    },
    "github": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "GITHUB_PERSONAL_ACCESS_TOKEN",
        "ghcr.io/github/github-mcp-server:latest"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "YOUR_TOKEN_HERE"
      }
    }
  }
}
```

## Skills used

```bash
npx skills add jabrena/cursor-rules-java --list
npx skills add jabrena/cursor-rules-java --skill 173-java-agents
npx skills add jabrena/cursor-rules-java --skill 110-java-maven-best-practices
```
