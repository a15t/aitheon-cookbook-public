package com.example.citation.model;

import java.util.List;

public class CitationResponse {
    private String content;
    private List<String> citations;
    private RecommendedBooks structuredData;
    
    public CitationResponse() {}
    
    public CitationResponse(String content, List<String> citations, RecommendedBooks structuredData) {
        this.content = content;
        this.citations = citations;
        this.structuredData = structuredData;
    }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public List<String> getCitations() { return citations; }
    public void setCitations(List<String> citations) { this.citations = citations; }
    
    public RecommendedBooks getStructuredData() { return structuredData; }
    public void setStructuredData(RecommendedBooks structuredData) { this.structuredData = structuredData; }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static class Builder {
        private String content;
        private List<String> citations;
        private RecommendedBooks structuredData;
        
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        
        public Builder citations(List<String> citations) {
            this.citations = citations;
            return this;
        }
        
        public Builder structuredData(RecommendedBooks structuredData) {
            this.structuredData = structuredData;
            return this;
        }
        
        public CitationResponse build() {
            return new CitationResponse(content, citations, structuredData);
        }
    }
}