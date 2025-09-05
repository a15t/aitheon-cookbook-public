package com.example.citation.cli;

import com.example.citation.model.BookRecommendation;
import com.example.citation.model.CitationResponse;
import com.example.citation.model.QuestionResponse;
import com.example.citation.model.SearchResult;
import com.example.citation.service.OpenAICitationService;
import java.util.Scanner;

public class CitationCLI {

    private final OpenAICitationService service;
    private final Scanner scanner;

    public CitationCLI(String apiKey) {
        this.service = new OpenAICitationService(apiKey);
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Ask a question");
            System.out.println("2. Get book recommendations");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1" -> askQuestion();
                case "2" -> getBookRecommendations();
                case "3" -> {
                    System.out.println("Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private void askQuestion() {
        System.out.print("\nYour question: ");
        String question = scanner.nextLine();

        if (question.trim().isEmpty()) {
            System.out.println("Question cannot be empty!");
            return;
        }

        System.out.println("\nThinking...");
        QuestionResponse response = service.askQuestion(question);
        
        System.out.println("\n=== Answer ===");
        System.out.println(response.getContent());
        
        if (response.hasCitations()) {
            System.out.println("\n=== Citations ===");
            for (int i = 0; i < response.getModelExtensions().getCitations().size(); i++) {
                System.out.println("[" + (i + 1) + "] " + response.getModelExtensions().getCitations().get(i));
            }
        }
        
        if (response.hasSearchResults()) {
            System.out.println("\n=== Search Results ===");
            for (int i = 0; i < response.getModelExtensions().getSearchResults().size(); i++) {
                SearchResult result = response.getModelExtensions().getSearchResults().get(i);
                System.out.println("[" + (i + 1) + "] " + result.getTitle());
                System.out.println("    " + result.getUrl());
                if (result.getDate() != null) {
                    System.out.println("    Date: " + result.getDate());
                }
                System.out.println();
            }
        }
    }

    private void getBookRecommendations() {
        System.out.print("\nWhat kind of books? ");
        String query = scanner.nextLine();

        if (query.trim().isEmpty()) {
            query = "popular fiction books";
        }

        System.out.println("\nGetting recommendations...");
        CitationResponse response = service.getBookRecommendations(query);

        if (
            response.getStructuredData() != null &&
            response.getStructuredData().getBooks() != null
        ) {
            System.out.println("\n=== Book Recommendations ===");
            System.out.println(
                response.getStructuredData().getSummary() + "\n"
            );

            int num = 1;
            for (BookRecommendation book : response
                .getStructuredData()
                .getBooks()) {
                System.out.println(num++ + ". " + book.getBookTitle());
                System.out.println("   by " + book.getBookAuthor());
                System.out.println("   " + book.getBookDescription());
                System.out.println();
            }
        } else {
            System.out.println("\n" + response.getContent());
        }
    }
}
