package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.service.GPTService;
import com.capstone.petropolis.service.MultiStepQAService;
import com.capstone.petropolis.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class MultiStepQAServiceImpl implements MultiStepQAService {

    @Autowired
    private PromptService promptService;

    @Autowired
    private GPTService gptService;

    @Override
    public String multiStepQA(List<String> historyQA, String currentQuery, String userIntent) throws InterruptedException, ExecutionException {
        String promptTemplate1 = promptService.getTemplateByPromptCode(userIntent + "_step1");
        String promptTemplate2 = promptService.getTemplateByPromptCode(userIntent + "_step2");

        String step1Prompt = buildStep1Prompt(promptTemplate1, historyQA, currentQuery);
        CompletableFuture<String> answerStep1Future = gptService.callOpenAi(step1Prompt);
        String answerStep1 = answerStep1Future.get();

        String step2Prompt = buildStep2Prompt(promptTemplate2, historyQA, currentQuery, answerStep1);
        CompletableFuture<String> answerStep2Future = gptService.callOpenAi(step2Prompt);
        String finalAnswer = answerStep2Future.get();

        return finalAnswer;
    }

    private String buildHistoryQA(List<String> historyQA) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < historyQA.size(); i++) {
            if (i % 2 == 0) {
                sb.append("<answer start>").append(historyQA.get(i)).append("<answer end>\n");
            } else {
                sb.append("<query start>").append(historyQA.get(i)).append("<query end>\n");
            }
        }

        return sb.toString();
    }

    private String buildStep1Prompt(String promptTemplate1, List<String> historyQA, String currentQuery) {
        String historyQAString = buildHistoryQA(historyQA);

        String processedPrompt = promptTemplate1.replace("HISTORYQA", historyQAString)
                .replace("CURRENTQ", currentQuery);

        return processedPrompt;
    }

    private String buildStep2Prompt(String promptTemplate2, List<String> historyQA, String currentQuery, String answerStep1) {
        String historyQAString = buildHistoryQA(historyQA);

        String processedPrompt = promptTemplate2.replace("HISTORYQA", historyQAString)
                .replace("CURRENTQ", currentQuery).replace("PREVIOUSRESULT", answerStep1);

        return processedPrompt;
    }
}
