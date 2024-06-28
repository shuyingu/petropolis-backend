package com.capstone.petropolis.service;

public interface RiskDetectService {
    boolean keywordRiskDetect(String answer);

    boolean perspectiveDetect(String answer);
}
