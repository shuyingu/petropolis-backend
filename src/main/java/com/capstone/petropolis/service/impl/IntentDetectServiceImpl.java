package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.entity.PromptTemplate;
import com.capstone.petropolis.repository.PromptTemplateRepository;
import com.capstone.petropolis.service.GPTService;
import com.capstone.petropolis.service.IntentDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IntentDetectServiceImpl implements IntentDetectService{

    @Autowired
    private PromptTemplateRepository promptTemplateRepository;

    @Autowired
    private GPTService gptService;

    @Override
    public String detectIntent(List<String> historyQA, String currentQuery) {
        PromptTemplate promptTemplate = promptTemplateRepository.getTemplate("intent_detect");

        String fullPrompt = buildPrompt(promptTemplate.getTemplate(), historyQA, currentQuery);

        String gptResponse = gptService.callOpenAi(fullPrompt).join();

        if (validateIntent(gptResponse)) {
            return gptResponse;
        } else {
            return "Server Error, Please try again";
        }
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

    private boolean validateIntent(String result) {
        // TODO: Design Intent type
        String[] validResults = {"choose pet", "pet information", "pet care", "others"};
        for (String validResult : validResults) {
            if (result.toLowerCase().equals(validResult)) {
                return true;
            }
        }
        return false;
    }
}