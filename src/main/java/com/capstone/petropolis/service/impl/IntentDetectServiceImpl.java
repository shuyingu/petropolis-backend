package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.entity.PromptTemplate;
import com.capstone.petropolis.repository.PromptTemplateRepository;
import com.capstone.petropolis.service.GPTService;
import com.capstone.petropolis.service.IntentDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Service
public class IntentDetectServiceImpl implements IntentDetectService {

    @Autowired
    private PromptTemplateRepository promptTemplateRepository;

    @Autowired
    private GPTService gptService;

    @Override
    public String detectIntent(List<String> historyQA, String currentQuery) throws Exception {
        PromptTemplate promptTemplate = promptTemplateRepository.getTemplate("intent_detect");

        String fullPrompt = buildPrompt(promptTemplate.getTemplate(), historyQA, currentQuery);

        CompletableFuture<String> gptResponseFuture = gptService.callOpenAi(fullPrompt);

        String gptResponse;

        gptResponse = gptResponseFuture.get(30, TimeUnit.SECONDS);

        return validateIntent(gptResponse);
    }

    private String buildPrompt(String template, List<String> historyQA, String currentQuery) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < historyQA.size(); i++) {
            if (i % 2 == 0) {
                sb.append("<answer start>").append(historyQA.get(i)).append("<answer end>\n");
            } else {
                sb.append("<query start>").append(historyQA.get(i)).append("<query end>\n");
            }
        }

        String processedPrompt = template.replace("HISTORYQA", sb.toString())
                .replace("CURRENTQ", currentQuery);

        return processedPrompt;
    }

    private String validateIntent(String result) {
        String validateString = "others";
        int cnt = 0;
        String[] validResults = {"choose pet", "pet information", "pet care", "others"};

        for (String validResult : validResults) {
            if (result.contains(validResult)) {
                cnt++;
                validateString = validResult;
            }
            if (cnt > 1) {
                return "others";  // More than one valid result found
            }
        }
        if (cnt == 1) {
            validateString = String.join("_",validateString.split(" "));
            return validateString;
        }
        else {
            return "others";
        }
    }
}