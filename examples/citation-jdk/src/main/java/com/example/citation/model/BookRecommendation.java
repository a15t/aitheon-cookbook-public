package com.example.citation.model;

import com.fasterxml.jackson.annotation.JsonPropertyDescription;

public class BookRecommendation {
    @JsonPropertyDescription("The title of the book")
    public String title;
    
    @JsonPropertyDescription("The author of the book")
    public String author;
    
    @JsonPropertyDescription("Brief description of the book")
    public String description;
    
    // Legacy fields for backward compatibility
    private String bookTitle;
    private String bookAuthor;
    private String bookDescription;
    
    public BookRecommendation() {}
    
    public BookRecommendation(String bookTitle, String bookAuthor, String bookDescription) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookDescription = bookDescription;
        // Set new fields for structured output compatibility
        this.title = bookTitle;
        this.author = bookAuthor;
        this.description = bookDescription;
    }
    
    public String getBookTitle() { return bookTitle; }
    public void setBookTitle(String bookTitle) { this.bookTitle = bookTitle; }
    
    public String getBookAuthor() { return bookAuthor; }
    public void setBookAuthor(String bookAuthor) { this.bookAuthor = bookAuthor; }
    
    public String getBookDescription() { return bookDescription; }
    public void setBookDescription(String bookDescription) { this.bookDescription = bookDescription; }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String bookTitle;
        private String bookAuthor;
        private String bookDescription;
        
        public Builder bookTitle(String bookTitle) {
            this.bookTitle = bookTitle;
            return this;
        }
        
        public Builder bookAuthor(String bookAuthor) {
            this.bookAuthor = bookAuthor;
            return this;
        }
        
        public Builder bookDescription(String bookDescription) {
            this.bookDescription = bookDescription;
            return this;
        }
        
        public BookRecommendation build() {
            return new BookRecommendation(bookTitle, bookAuthor, bookDescription);
        }
    }
}