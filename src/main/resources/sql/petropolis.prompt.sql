CREATE DATABASE IF NOT EXISTS `petropolis` CHARACTER SET UTF8MB4;

USE petropolis;

CREATE TABLE IF NOT EXISTS `prompt_templates` (
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key, prompt id',

    `template` VARCHAR(2048) NOT NULL COMMENT 'prompt template',
    `prompt_code` VARCHAR(64)  NOT NULL COMMENT 'prompt code, used for search'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT = 'prompt templates table';

# temperately insert prompt from sql (will be replaced later)
INSERT INTO prompt_templates (template, prompt_code)
VALUES ('intent_detect',
        'Context:\n' ||
        'HISTORYQA\n' ||
        'Current Query:\n' ||
        'CURRENTQ\n\n' ||
        'Task:\n' ||
        'Classify the user''s current intent into one of the following categories:\n' ||
        '- "choose pet": User wants to understand which type of pet would be most suitable.\n' ||
        '- "pet information": User seeks specific details about a particular type of pet, such as fur color, patterns, behavior, etc.\n' ||
        '- "pet care": User is looking for information on how to care for a specific type of pet.\n' ||
        '- "others": User''s intent does not fall into any of the above categories.\n' ||
        'Instructions to GPT:\n' ||
        'Based on the provided context and the current query, determine the user''s intent and provide one of the specified intent categories.'
)
