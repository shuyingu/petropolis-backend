package com.capstone.petropolis.service.chat;

import com.capstone.petropolis.entity.PromptTemplate;
import com.capstone.petropolis.repository.PromptTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptService {
    @Autowired
    private PromptTemplateRepository promptTemplateRepository;

    public String getTemplateByPromptCode(String promptCode) {
        PromptTemplate promptTemplate = promptTemplateRepository.getTemplate(promptCode);
        return promptTemplate.getTemplate();
    }
}