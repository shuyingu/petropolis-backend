package com.capstone.petropolis.service;

import java.util.List;

public interface IntentDetectService {
    String detectIntent(List<String> historyQA, String cuttentQuery);
}
