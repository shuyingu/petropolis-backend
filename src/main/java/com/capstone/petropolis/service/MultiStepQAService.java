package com.capstone.petropolis.service;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface MultiStepQAService {
    String multiStepQA(List<String> historyQA, String currentQuery, String userIntent) throws Exception;
}
