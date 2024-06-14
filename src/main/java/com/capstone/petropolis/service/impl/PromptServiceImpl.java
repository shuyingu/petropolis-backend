package com.capstone.petropolis.service.impl;

import com.capstone.petropolis.entity.PromptTemplate;
import com.capstone.petropolis.repository.PromptTemplateRepository;
import com.capstone.petropolis.service.PromptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptServiceImpl implements PromptService{
    @Autowired
    private PromptTemplateRepository promptTemplateRepository;

    @Override
    public String getTemplateByPromptCode(String promptCode) {
        PromptTemplate promptTemplate = promptTemplateRepository.getTemplate(promptCode);
        return promptTemplate.getTemplate();
    }
}