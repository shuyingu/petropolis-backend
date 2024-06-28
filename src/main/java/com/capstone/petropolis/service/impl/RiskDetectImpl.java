package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.service.RiskDetectService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Service
public class RiskDetectImpl implements RiskDetectService {
    @Value("${risk.sensitive_words}")
    private String sensitiveWordsJson;

    @Value("${risk.url}")
    private String url;

    @Value("${risk.key")
    private String apiKey;

    @Autowired
    private RestTemplate restTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Set<Pattern> sensitivePatterns = new HashSet<>();

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        List<String> wordsList = objectMapper.readValue(sensitiveWordsJson, new TypeReference<List<String>>() {});
        for (String word : wordsList) {
            sensitivePatterns.add(Pattern.compile(word, Pattern.CASE_INSENSITIVE));
        }
    }

    @Override
    public boolean keywordRiskDetect(String answer) {
        for (Pattern pattern : sensitivePatterns) {
            if (pattern.matcher(answer).find()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean perspectiveDetect(String answer) throws IllegalArgumentException {
        Map<String, Object> requestPayload = new HashMap<>();
        Map<String, String> comment = new HashMap<>();
        comment.put("text", answer);
        requestPayload.put("comment", comment);
        Map<String, Map<String, Integer>> requestedAttributes = new HashMap<>();
        requestedAttributes.put("TOXICITY", new HashMap<>());
        requestedAttributes.put("INSULT", new HashMap<>());
        requestedAttributes.put("THREAT", new HashMap<>());
        requestedAttributes.put("NEGATIVE", new HashMap<>());
        requestPayload.put("requestedAttributes", requestedAttributes);

        Map<String, Object> response = restTemplate.postForObject(url, requestPayload, Map.class, apiKey);

        if (response != null) {
            Map<String, Object> attributeScores = objectMapper.convertValue(response.get("attributeScores"), new TypeReference<Map<String, Object>>() {});
            for (String attribute : attributeScores.keySet()) {
                Map<String, Object> attributeScore = objectMapper.convertValue(attributeScores.get(attribute), new TypeReference<Map<String, Object>>() {});
                Map<String, Object> summaryScore = objectMapper.convertValue(attributeScore.get("summaryScore"), new TypeReference<Map<String, Object>>() {});
                double score = (double) summaryScore.get("value");
                if (score > 0.8) { // 0.8以上的分数表示有风险
                    return true;
                }
            }
        }

        return false;
    }
}
