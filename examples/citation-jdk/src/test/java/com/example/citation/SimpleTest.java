package com.example.citation;

import com.example.citation.model.*;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleTest {
    
    @Test
    public void testBookRecommendationModel() {
        BookRecommendation book = BookRecommendation.builder()
            .bookTitle("Project Hail Mary")
            .bookAuthor("Andy Weir")
            .bookDescription("A lone astronaut must save humanity")
            .build();
        
        assertEquals("Project Hail Mary", book.getBookTitle());
        assertEquals("Andy Weir", book.getBookAuthor());
        assertNotNull(book.getBookDescription());
    }
    
    @Test
    public void testCitationResponse() {
        List<String> citations = Arrays.asList(
            "https://example.com/source1",
            "https://example.com/source2"
        );
        
        CitationResponse response = CitationResponse.builder()
            .content("Test content")
            .citations(citations)
            .build();
        
        assertNotNull(response);
        assertEquals("Test content", response.getContent());
        assertEquals(2, response.getCitations().size());
    }
    
    @Test
    public void testRecommendedBooks() {
        BookRecommendation book1 = BookRecommendation.builder()
            .bookTitle("The Martian")
            .bookAuthor("Andy Weir")
            .bookDescription("Survival on Mars")
            .build();
        
        BookRecommendation book2 = BookRecommendation.builder()
            .bookTitle("Dune")
            .bookAuthor("Frank Herbert")
            .bookDescription("Desert planet epic")
            .build();
        
        List<BookRecommendation> books = Arrays.asList(book1, book2);
        RecommendedBooks recommendations = new RecommendedBooks(books, "Top picks");
        
        assertEquals(2, recommendations.getBooks().size());
        assertEquals("Top picks", recommendations.getSummary());
    }
}