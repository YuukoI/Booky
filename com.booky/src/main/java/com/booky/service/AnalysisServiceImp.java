package com.booky.service;

import com.booky.entity.Analysis;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalysisServiceImp implements AnalysisService {

    private final ChatClient chatClient;
    private final ObjectMapper objectMapper;

    public AnalysisServiceImp(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Analysis analyzeText(String feedbackText) {
        log.info("Starting analysis for text: {}", feedbackText);
        
        try {
            log.info("Calling OpenAI...");
            String response = chatClient.prompt()
                    .user(u -> u.text("""
                    Analyze this book feedback: "{text}"
                    
                    IMPORTANT: Respond ONLY with a valid JSON object. 
                    Do not include markdown blocks like ```json.
                    
                    1. First, check for any profanity, hate speech, offensive language, or threatening content.
                    2. If ANY offensive content is detected, set "isSuitable" to false.
                    3. If the feedback is clean and appropriate, set "isSuitable" to true and perform the analysis.
                    
                    Fields:
                    - "sentiment": (POSITIVE, NEGATIVO or NEUTRAL)
                    - "summary": (brief summary)
                    - "score": (integer 1-10)
                    - "isSuitable": (boolean - false if offensive content detected)
                    """)
                            .param("text", feedbackText))
                    .call()
                    .content();
            
            log.info("OpenAI response received: {}", response);

            String cleanJson = response.replaceAll("```json", "").replaceAll("```", "").trim();
            Analysis analysis = objectMapper.readValue(cleanJson, Analysis.class);
            log.info("Analysis parsed successfully: {}", analysis);
            return analysis;
            
        } catch (Exception e) {
            log.error("Error during analysis: {}", e.getMessage(), e);

            if (e.getMessage().contains("insufficient_quota") || e.getMessage().contains("429")) {
                log.info("OpenAI quota exceeded, performing basic profanity detection");

                String lowerText = feedbackText.toLowerCase();
                boolean hasOffensive = lowerText.matches(".*\\b(mierda|puta|puto|culiao|concha|chucha|muera|matar|carajo|verga|pendejo|hijoputa|coño|pito|cagar|cagada|mier|shit|fuck|damn|hell|bitch|asshole|kill|die)\\b.*");
                
                return Analysis.builder()
                        .sentiment(hasOffensive ? "NEGATIVE" : "POSITIVE")
                        .summary(hasOffensive ? "Feedback contains potentially offensive content" : "Excellent feedback from user about the book")
                        .score(hasOffensive ? 1 : 8)
                        .isSuitable(!hasOffensive)
                        .build();
            }
            
            return Analysis.builder()
                    .sentiment("NEUTRAL")
                    .summary("Analysis failed: " + e.getMessage())
                    .score(5)
                    .isSuitable(false)
                    .build();
        }
    }
}
