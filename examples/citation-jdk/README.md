# OpenAI Citation Demo - Perplexity Style Implementation

A Java demonstration of implementing Perplexity-style citations with structured output using the OpenAI Java SDK.

## Quick Start

```bash
# Set up configuration
cp .env.example .env
# Edit .env and add your OpenAI API key

# Build and run
mvn clean package
java -jar target/openai-citation-demo-1.0-SNAPSHOT.jar
```

## Features

- **Perplexity-style Citations**: Inline citation markers [1], [2] with source URLs
- **Structured Output**: JSON schema-based responses for book recommendations
- **Interactive CLI**: User-friendly command-line interface
- **Multiple Query Modes**: Simple text or structured JSON responses
- **Demo Examples**: Pre-configured examples to showcase functionality

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- OpenAI API Key

## Configuration

### Option 1: Using .env file (Recommended)
1. Copy the example environment file:
   ```bash
   cp .env.example .env
   ```
2. Edit `.env` and add your configuration.

**`.env.example` template:**
```bash
# OpenAI API Configuration
# Copy this file to .env and fill in your values

OPENAI_API_KEY=sk-your-api-key-here
OPENAI_MODEL=perplexity/sonar
OPENAI_BASE_URL=https://api.platform.a15t.com/v1
```

### Option 2: Using environment variables
```bash
export OPENAI_API_KEY='sk-your-api-key-here'
export OPENAI_MODEL='perplexity/sonar'
export OPENAI_BASE_URL='https://api.platform.a15t.com/v1' 
```

### Configuration Priority
The application reads configuration in this order:
1. `.env` file (if exists)
2. Environment variables
3. Command line arguments (for API key only)

## Build

```bash
mvn clean package
```

## Test

Run unit tests to verify the implementation:
```bash
mvn test
```

All tests should pass showing the models and JSON serialization work correctly.

## Run

```bash
java -jar target/openai-citation-demo-1.0-SNAPSHOT.jar
```

### Option 3: Using JAR with API key argument
```bash
java -jar target/openai-citation-demo-1.0-SNAPSHOT.jar sk-your-api-key
```

## License

MIT
