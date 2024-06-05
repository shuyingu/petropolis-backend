package com.capstone.petropolis.service.chat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
public class GPTServiceTest {

    @Autowired
    private GPTService gptService;

    @Test
    public void testCallGPT() throws ExecutionException, InterruptedException {
        CompletableFuture<String> futureResponse = gptService.callOpenAi("Hello, world!");
        String response = futureResponse.get();
        assertNotNull(response);
    }
}