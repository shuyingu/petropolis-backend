package com.capstone.petropolis.controller;

import com.capstone.petropolis.entity.ChatMessage;
import com.capstone.petropolis.service.IntentDetectService;
import com.capstone.petropolis.service.MultiStepQAService;
import com.capstone.petropolis.service.RiskDetectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

@RestController
@RequestMapping("/chat")
public class ChatController {
    private static final Logger log = LogManager.getLogger();

    @Autowired
    private IntentDetectService intentDetectService;

    @Autowired
    private MultiStepQAService multiStepQAService;

    @Autowired
    private RiskDetectService riskDetectService;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatMessage request) {
        log.info(request.toString());
        List<String> historyQA = request.getHistoryQA();
        if (historyQA == null) {
            historyQA = new ArrayList<>();
        }
        try {
            String query = request.getContent();

            ChatMessage response = new ChatMessage();
            response.setChatId(String.valueOf(System.currentTimeMillis()));
            response.setAnswer(true);

            historyQA.add(query);
            response.setHistoryQA(historyQA);

            // Intent Detection
            String intent = intentDetectService.detectIntent(historyQA, query);
            if (intent.equals("others")) {
                response.setContent("Please refresh your chatbox and answer a question related to pet selection or pet information.\n");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // MultistepQA
            String answerContent = multiStepQAService.multiStepQA(historyQA, query, intent);

            // RiskDetection
            boolean haveRisk = riskDetectService.keywordRiskDetect(answerContent);
            if (haveRisk) {
                response.setContent("Wrong query");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setContent(answerContent);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (TimeoutException te) {
            return ResponseEntity.badRequest().body("Timeout, please retry or refresh the chatbox.");
        } catch (Exception e) {
            if (e.getMessage().contains("timeout")) {
                return ResponseEntity.badRequest().body("Timeout, please retry or refresh the chatbox.");
            }
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @MessageMapping("/chat-websocket")
    @SendTo("/topic/chat")
    public ResponseEntity<?> chatWebSocket(ChatMessage request) {
        log.info(request.toString());
        List<String> historyQA = request.getHistoryQA();
        if (historyQA == null) {
            historyQA = new ArrayList<>();
        }
        try {
            String query = request.getContent();

            ChatMessage response = new ChatMessage();
            response.setChatId(String.valueOf(System.currentTimeMillis()));
            response.setAnswer(true);

            historyQA.add(query);
            response.setHistoryQA(historyQA);

            // Intent Detection
            String intent = intentDetectService.detectIntent(historyQA, query);
            if (intent.equals("others")) {
                response.setContent("Please refresh your chatbox and answer a question related to pet selection or pet information.\n");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }

            // MultistepQA
            String answerContent = multiStepQAService.multiStepQA(historyQA, query, intent);

            // RiskDetection
            boolean haveRisk = riskDetectService.keywordRiskDetect(answerContent);
            if (haveRisk) {
                response.setContent("Wrong query");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            } else {
                response.setContent(answerContent);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
