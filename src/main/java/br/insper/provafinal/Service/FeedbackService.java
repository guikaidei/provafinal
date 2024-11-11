package br.insper.provafinal.Service;


import br.insper.provafinal.Feedback;
import br.insper.provafinal.Repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback createFeedback(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedbacks() {
        return feedbackRepository.findAll();
    }

    public Feedback getFeedbackById(String id) {
        return feedbackRepository.findById(id).orElse(null);
    }

    public void deleteFeedback(String id) {
        feedbackRepository.deleteById(id);
    }


}
