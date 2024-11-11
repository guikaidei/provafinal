package br.insper.provafinal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Feedback {
    @Id
    private String id;

    private String feedback;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}
