package com.example.citation.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import java.util.List;

public class BookListResponse {
    @JsonPropertyDescription("List of book recommendations")
    public List<BookRecommendation> books;
    
    @JsonPropertyDescription("Brief summary of the recommendations")
    public String summary;
    
    public BookListResponse() {}
    
    public BookListResponse(List<BookRecommendation> books, String summary) {
        this.books = books;
        this.summary = summary;
    }
    
    public List<BookRecommendation> getBooks() { return books; }
    public void setBooks(List<BookRecommendation> books) { this.books = books; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}