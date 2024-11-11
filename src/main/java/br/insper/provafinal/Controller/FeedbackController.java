package br.insper.provafinal.Controller;


import br.insper.provafinal.Feedback;
import br.insper.provafinal.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;


    @GetMapping
    public List<Feedback> getAllFeedbacks() {
        String papel = getPapel();
        if (papel.equals("ADMIN") || papel.equals("DEVELOPER")) {
            return feedbackService.getAllFeedbacks();
        }
        return null;
    }

    @GetMapping("/{id}")
    public Feedback getFeedback(@PathVariable String id) {
        String papel = getPapel();
        if (papel.equals("ADMIN") || papel.equals("DEVELOPER")) {
            return feedbackService.getFeedbackById(id);
        }
        return null;
    }

    @PostMapping
    public Feedback createFeedback(@RequestBody Feedback feedback) {
        String papel = getPapel();
        if (!papel.equals("ADMIN")) {
            return null;
        }
        return feedbackService.createFeedback(feedback);
    }

    @DeleteMapping("/{id}")
    public void deleteFeedback(@PathVariable String id) {
        String papel = getPapel();
        if (!papel.equals("ADMIN")) {
            return;
        }
        feedbackService.deleteFeedback(id);
    }


    private String getPapel() {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://184.72.80.215/usuario/validate";
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        Map<String, String> responseBody = response.getBody();
        return responseBody != null ? responseBody.get("papel") : null;
    }
}
