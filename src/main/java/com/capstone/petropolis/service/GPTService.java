package com.capstone.petropolis.service;

import java.util.concurrent.CompletableFuture;

public interface GPTService {
    CompletableFuture<String> callOpenAi(String prompt);
}