package com.example.citation.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class ModelExtensions {
    @JsonProperty("citations")
    private List<String> citations;
    
    @JsonProperty("search_results")
    private List<SearchResult> searchResults;
    
    public ModelExtensions() {}
    
    public ModelExtensions(List<String> citations, List<SearchResult> searchResults) {
        this.citations = citations;
        this.searchResults = searchResults;
    }
    
    public List<String> getCitations() {
        return citations;
    }
    
    public void setCitations(List<String> citations) {
        this.citations = citations;
    }
    
    public List<SearchResult> getSearchResults() {
        return searchResults;
    }
    
    public void setSearchResults(List<SearchResult> searchResults) {
        this.searchResults = searchResults;
    }
    
    @Override
    public String toString() {
        return "ModelExtensions{" +
                "citations=" + citations +
                ", searchResults=" + searchResults +
                '}';
    }
}