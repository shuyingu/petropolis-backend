package com.capstone.petropolis.repository;

import com.capstone.petropolis.entity.PromptTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PromptTemplateRepository extends JpaRepository<PromptTemplate, Long> {
    @Query(value = "SELECT * FROM prompt_templates WHERE prompt_code = ?1", nativeQuery = true)
    PromptTemplate getTemplate(String promptCode);
}