package com.example.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RecommendedBooks {
    @JsonProperty("books")
    private List<BookRecommendation> books;
    
    @JsonProperty("summary")
    private String summary;
    
    public RecommendedBooks() {}
    
    public RecommendedBooks(List<BookRecommendation> books, String summary) {
        this.books = books;
        this.summary = summary;
    }
    
    public List<BookRecommendation> getBooks() { return books; }
    public void setBooks(List<BookRecommendation> books) { this.books = books; }
    
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
}