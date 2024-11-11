package br.insper.provafinal.Controller;

import br.insper.provafinal.Feedback;
import br.insper.provafinal.Service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @GetMapping
    public ResponseEntity<List<Feedback>> getAllFeedbacks(@RequestHeader("Authorization") String token) {
        String papel = getPapel(token);
        if ("ADMIN".equals(papel) || "DEVELOPER".equals(papel)) {
            return ResponseEntity.ok(feedbackService.getAllFeedbacks());
        }
        return ResponseEntity.status(403).build(); // Retorna 403 Forbidden se o papel não tiver autorização
    }

    @GetMapping("/{id}")
    public ResponseEntity<Feedback> getFeedback(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String papel = getPapel(token);
        if ("ADMIN".equals(papel) || "DEVELOPER".equals(papel)) {
            Feedback feedback = feedbackService.getFeedbackById(id);
            return feedback != null ? ResponseEntity.ok(feedback) : ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(403).build(); // Retorna 403 Forbidden
    }

    @PostMapping
    public ResponseEntity<Feedback> createFeedback(@RequestBody Feedback feedback, @RequestHeader("Authorization") String token) {
        String papel = getPapel(token);
        if (!"ADMIN".equals(papel)) {
            return ResponseEntity.status(403).build(); // Apenas ADMIN pode criar feedbacks
        }
        return ResponseEntity.ok(feedbackService.createFeedback(feedback));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFeedback(@PathVariable String id, @RequestHeader("Authorization") String token) {
        String papel = getPapel(token);
        if (!"ADMIN".equals(papel)) {
            return ResponseEntity.status(403).build(); // Apenas ADMIN pode deletar feedbacks
        }
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build(); // Retorna 204 No Content após exclusão
    }

    private String getPapel(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://184.72.80.215/usuario/validate";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token); // Ajuste se necessário para incluir "Bearer "
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            Map<String, String> responseBody = response.getBody();
            return responseBody != null ? responseBody.get("papel") : null;
        } catch (Exception e) {
            System.err.println("Erro ao validar token: " + e.getMessage());
            return null;
        }
    }
}
