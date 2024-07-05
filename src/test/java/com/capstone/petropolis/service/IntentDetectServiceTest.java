package com.capstone.petropolis.service;

import com.capstone.petropolis.service.impl.IntentDetectServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class IntentDetectServiceTest {

    @Autowired
    private IntentDetectServiceImpl intentDetectService;

    @Test
    public void testDetectIntent_choose_pet() {
        List<String> historyQA = new ArrayList<>();
        String currentQuery = "I want to buy a pet";

        String result = "";

        try {
            result = intentDetectService.detectIntent(historyQA, currentQuery);
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("choose pet", result);
    }

    @Test
    public void testDetectIntent_InvalidResponse() {
        List<String> historyQA = new ArrayList<>();
        String currentQuery = "You are a bad guy.";

        String result = "";

        try {
            result = intentDetectService.detectIntent(historyQA, currentQuery);
        } catch (Exception e) {
            System.out.println(e);
        }

        assertEquals("others", result);
    }
}