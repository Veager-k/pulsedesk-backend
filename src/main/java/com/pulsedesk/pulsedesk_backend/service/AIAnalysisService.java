package com.pulsedesk.pulsedesk_backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pulsedesk.pulsedesk_backend.dto.GeminiRequest;
import com.pulsedesk.pulsedesk_backend.dto.TicketAnalysisResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class AIAnalysisService {

    @Value("${gemini.api.key}")
    private String apiKey;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AIAnalysisService(WebClient.Builder builder, ObjectMapper objectMapper) {
        this.webClient = builder
                .baseUrl("https://generativelanguage.googleapis.com")
                .build();

        this.objectMapper = objectMapper;
    }

    public TicketAnalysisResult analyzeComment(String comment) {

        try {

            String prompt = buildPrompt(comment);

            GeminiRequest request = new GeminiRequest(
                    List.of(
                            new GeminiRequest.Content(
                                    List.of(
                                            new GeminiRequest.Part(prompt)
                                    )
                            )
                    )
            );

            String response = webClient.post()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1beta/models/gemini-2.5-flash:generateContent")
                            .queryParam("key", apiKey)
                            .build())
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            return parseResponse(response);

        } catch (Exception e) {
            throw new RuntimeException("AI analysis failed", e);
        }
    }

    private String buildPrompt(String comment) {

        return """
                Analyze this user comment.

                Return ONLY valid JSON.

                JSON format:
                {
                  "shouldCreateTicket": true,
                  "title": "",
                  "category": "",
                  "priority": "",
                  "summary": ""
                }

                Categories:
                - bug
                - feature
                - billing
                - account
                - other

                Priorities:
                - low
                - medium
                - high

                Comment:
                "%s"
                """.formatted(comment);
    }

    private TicketAnalysisResult parseResponse(String response) {

        try {

            JsonNode root = objectMapper.readTree(response);

            String text = root
                    .get("candidates")
                    .get(0)
                    .get("content")
                    .get("parts")
                    .get(0)
                    .get("text")
                    .asText();

            text = text
                    .replace("```json", "")
                    .replace("```", "")
                    .trim();

            return objectMapper.readValue(text, TicketAnalysisResult.class);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse Gemini response", e);
        }
    }
}