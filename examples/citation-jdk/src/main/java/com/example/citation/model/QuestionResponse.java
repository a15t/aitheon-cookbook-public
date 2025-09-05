package com.example.citation.model;

public class QuestionResponse {
    private String content;
    private ModelExtensions modelExtensions;
    
    public QuestionResponse() {}
    
    public QuestionResponse(String content, ModelExtensions modelExtensions) {
        this.content = content;
        this.modelExtensions = modelExtensions;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public ModelExtensions getModelExtensions() {
        return modelExtensions;
    }
    
    public void setModelExtensions(ModelExtensions modelExtensions) {
        this.modelExtensions = modelExtensions;
    }
    
    public boolean hasCitations() {
        return modelExtensions != null && 
               modelExtensions.getCitations() != null && 
               !modelExtensions.getCitations().isEmpty();
    }
    
    public boolean hasSearchResults() {
        return modelExtensions != null && 
               modelExtensions.getSearchResults() != null && 
               !modelExtensions.getSearchResults().isEmpty();
    }
    
    @Override
    public String toString() {
        return "QuestionResponse{" +
                "content='" + content + '\'' +
                ", modelExtensions=" + modelExtensions +
                '}';
    }
}