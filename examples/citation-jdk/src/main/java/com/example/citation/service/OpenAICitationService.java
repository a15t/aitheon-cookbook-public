package com.example.citation.service;

import com.example.citation.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.core.JsonValue;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.openai.models.chat.completions.StructuredChatCompletion;
import com.openai.models.chat.completions.StructuredChatCompletionCreateParams;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.*;

public class OpenAICitationService {

    private final OpenAIClient openAIClient;
    private final ObjectMapper objectMapper;
    private final String modelName;

    public OpenAICitationService(String apiKey) {
        // Load environment variables
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

        // Get configurations from .env file
        String baseUrl = dotenv.get("OPENAI_BASE_URL");
        String configuredModel = dotenv.get("OPENAI_MODEL");

        // Build the client with custom endpoint if provided
        OpenAIOkHttpClient.Builder clientBuilder =
            OpenAIOkHttpClient.builder().apiKey(apiKey);

        if (baseUrl != null && !baseUrl.trim().isEmpty()) {
            clientBuilder.baseUrl(baseUrl);
        }

        this.openAIClient = clientBuilder.build();
        this.objectMapper = new ObjectMapper();

        // Use configured model or default
        this.modelName = (configuredModel != null &&
                !configuredModel.trim().isEmpty())
            ? configuredModel
            : "gpt-4o-mini";
    }

    public QuestionResponse askQuestion(String question) {
        try {
            ChatCompletionCreateParams request =
                ChatCompletionCreateParams.builder()
                    .model(modelName)
                    .addSystemMessage(
                        "You are a helpful assistant. Include citations [1], [2] etc. when providing information."
                    )
                    .addUserMessage(question)
                    .maxCompletionTokens(500)
                    .temperature(0.7)
                    .build();

            // Add extra body parameters for model extensions
            ChatCompletionCreateParams requestWithExtras = request
                .toBuilder()
                .putAdditionalBodyProperty(
                    "model_extensions",
                    JsonValue.from(Map.of("return_citations", true))
                )
                .build();

            ChatCompletion completion = openAIClient
                .chat()
                .completions()
                .create(requestWithExtras);

            String content = completion
                .choices()
                .get(0)
                .message()
                .content()
                .orElse("No response");
            ModelExtensions extensions = null;

            // Extract and parse additional properties
            Map<String, JsonValue> extraProperties =
                completion._additionalProperties();

            if (extraProperties.containsKey("model_extensions")) {
                JsonValue modelExtensions = extraProperties.get(
                    "model_extensions"
                );
                try {
                    // Convert to strongly typed ModelExtensions object
                    extensions = objectMapper.convertValue(
                        modelExtensions,
                        ModelExtensions.class
                    );
                } catch (Exception parseError) {
                    System.err.println(
                        "Error parsing model_extensions: " +
                        parseError.getMessage()
                    );
                }
            }

            return new QuestionResponse(content, extensions);
        } catch (Exception e) {
            return new QuestionResponse("Error: " + e.getMessage(), null);
        }
    }

    public CitationResponse getBookRecommendations(String query) {
        try {
            String systemPrompt =
                "You are a book recommendation assistant. Recommend 3 books based on the user's request.";

            // Use structured output with the BookListResponse class
            StructuredChatCompletionCreateParams<BookListResponse> request =
                ChatCompletionCreateParams.builder()
                    .model(modelName)
                    .addSystemMessage(systemPrompt)
                    .addUserMessage(query)
                    .maxCompletionTokens(800)
                    .temperature(0.7)
                    .responseFormat(BookListResponse.class)
                    .build();

            StructuredChatCompletion<BookListResponse> completion = openAIClient
                .chat()
                .completions()
                .create(request);
            BookListResponse structuredResponse = completion
                .choices()
                .get(0)
                .message()
                .content()
                .orElse(new BookListResponse());

            // Convert to legacy format for backward compatibility
            List<BookRecommendation> legacyBooks = new ArrayList<>();
            if (structuredResponse.books != null) {
                for (BookRecommendation book : structuredResponse.books) {
                    // Create legacy format with proper field mapping
                    BookRecommendation legacyBook = BookRecommendation.builder()
                        .bookTitle(
                            book.title != null
                                ? book.title
                                : book.getBookTitle()
                        )
                        .bookAuthor(
                            book.author != null
                                ? book.author
                                : book.getBookAuthor()
                        )
                        .bookDescription(
                            book.description != null
                                ? book.description
                                : book.getBookDescription()
                        )
                        .build();
                    legacyBooks.add(legacyBook);
                }
            }

            RecommendedBooks recommendedBooks = new RecommendedBooks();
            recommendedBooks.setBooks(legacyBooks);
            recommendedBooks.setSummary(structuredResponse.summary);

            return CitationResponse.builder()
                .content(objectMapper.writeValueAsString(structuredResponse))
                .structuredData(recommendedBooks)
                .build();
        } catch (Exception e) {
            return CitationResponse.builder()
                .content("Error getting recommendations: " + e.getMessage())
                .build();
        }
    }
}
