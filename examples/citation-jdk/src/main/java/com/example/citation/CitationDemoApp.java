package com.example.citation;

import com.example.citation.cli.CitationCLI;
import io.github.cdimascio.dotenv.Dotenv;

public class CitationDemoApp {
    public static void main(String[] args) {
        System.out.println("\n=============================");
        System.out.println("   GIP Citation Demo");
        System.out.println("=============================");
        
        // Load environment variables
        Dotenv dotenv = Dotenv.configure()
                .ignoreIfMissing()
                .load();
        
        // Get API key from .env or environment
        String apiKey = dotenv.get("OPENAI_API_KEY");
        if (apiKey == null) {
            apiKey = System.getenv("OPENAI_API_KEY");
        }
        
        // Check command line argument as fallback
        if ((apiKey == null || apiKey.trim().isEmpty()) && args.length > 0) {
            apiKey = args[0];
        }
        
        if (apiKey == null || apiKey.trim().isEmpty()) {
            System.err.println("\nERROR: OpenAI API key not found!");
            System.err.println("\nProvide your API key using:");
            System.err.println("1. Set OPENAI_API_KEY in .env file");
            System.err.println("2. Set environment variable: export OPENAI_API_KEY='your-key'");
            System.err.println("3. Pass as argument: java -jar demo.jar your-api-key");
            System.exit(1);
        }
        
        try {
            CitationCLI cli = new CitationCLI(apiKey);
            cli.run();
        } catch (Exception e) {
            System.err.println("\nError: " + e.getMessage());
            System.exit(1);
        }
    }
}