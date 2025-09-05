#!/bin/bash

# OpenAI Citation Demo Runner Script

# Color codes for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}========================================${NC}"
echo -e "${GREEN}     OpenAI Citation Demo Runner${NC}"
echo -e "${GREEN}========================================${NC}"
echo ""

# Check for .env file
if [ -f ".env" ]; then
    echo -e "${BLUE}Found .env file - configuration will be loaded from it${NC}"
    echo ""
else
    echo -e "${YELLOW}No .env file found. You can create one by copying .env.example:${NC}"
    echo "  cp .env.example .env"
    echo ""
    
    # Check if OPENAI_API_KEY is set in environment
    if [ -z "$OPENAI_API_KEY" ]; then
        echo -e "${YELLOW}WARNING: OPENAI_API_KEY not found in environment${NC}"
        echo "Please enter your OpenAI API key:"
        read -s OPENAI_API_KEY
        export OPENAI_API_KEY
    fi
fi

# Show configuration source
echo -e "${BLUE}Configuration Priority:${NC}"
echo "1. .env file (if exists)"
echo "2. Environment variables"
echo "3. Command line arguments (API key only)"
echo ""

# Optional: Show current configuration (without exposing the full API key)
if [ ! -z "$OPENAI_API_KEY" ]; then
    KEY_PREFIX="${OPENAI_API_KEY:0:7}"
    echo -e "${GREEN}API Key: ${KEY_PREFIX}...${NC}"
fi

if [ ! -z "$OPENAI_MODEL" ]; then
    echo -e "${GREEN}Model: $OPENAI_MODEL${NC}"
fi

if [ ! -z "$OPENAI_BASE_URL" ]; then
    echo -e "${GREEN}Custom Endpoint: $OPENAI_BASE_URL${NC}"
fi

# Build the project if needed
if [ ! -f "target/openai-citation-demo-1.0-SNAPSHOT.jar" ]; then
    echo -e "${YELLOW}Building project...${NC}"
    mvn clean package -DskipTests
    if [ $? -ne 0 ]; then
        echo -e "${RED}Build failed! Please check the error messages above.${NC}"
        exit 1
    fi
fi

# Run the application
echo ""
echo -e "${GREEN}Starting application...${NC}"
echo ""
java -jar target/openai-citation-demo-1.0-SNAPSHOT.jar