# Spring-boot Acceptance tests example

## How to run in local?

```
./mvnw verify -pl app
./mvnw verify -pl acceptance-tests
```

## MCP Servers

### maven-tools-mcp

```json
{
  "mcpServers": {
    "maven-tools": {
      "command": "docker",
      "args": ["run", "-i", "--rm", "arvindand/maven-tools-mcp:latest"]
    }
  }
}
```

## Skills used

```bash
npx skills add jabrena/cursor-rules-java --list
npx skills add jabrena/cursor-rules-java --skill 173-java-agents
```

