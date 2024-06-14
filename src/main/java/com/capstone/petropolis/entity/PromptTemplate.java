package com.capstone.petropolis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "prompt_templates")
public class PromptTemplate {
    @Id
    @Column(name = "id", nullable = false, unique = true, columnDefinition = "BIGINT UNSIGNED COMMENT 'Primary Key, prompt id'")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "prompt_code", nullable = false, length = 64, columnDefinition = "VARCHAR(64) COMMENT 'prompt code, used for search'")
    private String prompt_code;

    @Column(name = "template", nullable = false, length = 64, columnDefinition = "VARCHAR(2048) COMMENT 'prompt template'")
    private String template;

    // Getters and setters
    public long getId() {
        return id;
    }

    public String getPromptCode() {
        return prompt_code;
    }

    public void setPromptCode(String promptCode) {
        this.prompt_code = promptCode;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}