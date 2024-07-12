CREATE
DATABASE IF NOT EXISTS `petropolis` CHARACTER SET UTF8MB4;

USE
petropolis;

drop table if exists `prompt_templates`;

CREATE TABLE IF NOT EXISTS `prompt_templates`
(
    `id` BIGINT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT 'Primary key, prompt id',
    `template` VARCHAR(2048) NOT NULL COMMENT 'prompt template',
    `prompt_code` VARCHAR(64) NOT NULL COMMENT 'prompt code, used for search'
) ENGINE=InnoDB DEFAULT CHARSET=UTF8MB4 COMMENT = 'prompt templates table';

-- temperately insert prompt from sql for test (will be replaced later)
INSERT INTO prompt_templates (template, prompt_code)
VALUES (CONCAT(
            'Context:\n',
            'HISTORYQA\n',
            'Current Query:\n',
            'CURRENTQ\n\n',
            'Task:\n',
            'Classify the user''s current intent into one of the following categories:\n',
            '- "choose pet": User wants to understand which type of pet would be most suitable.\n',
            '- "pet information": User seeks specific details about a particular type of pet, such as fur color, patterns, behavior, etc.\n',
            '- "pet care": User is looking for information on how to care for a specific type of pet.\n',
            '- "others": User''s intent does not fall into any of the above categories.\n',
            'Instructions to GPT:\n',
            'Based on the provided context and the current query, determine the user''s intent and provide one of the specified intent categories.'
        ),
        'intent_detect'
);

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the user intent is to choose a pet, use the following historical dialogue (Context) and current query (Current Query) to identify the knowledge needed for the current pet selection. Determine if additional information is required from the user to make a more accurate pet selection. If additional information is needed, specify what information is required. Ensure the response is focused on pet selection, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Rules:',
                '1. Focus on the topic of pet selection.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is helpful and supportive.\n',
                'Task: Based on the above context and current query, identify the necessary knowledge for choosing a pet. Assess if additional information from the user is needed for a more precise pet recommendation. If yes, specify the additional information required.'
        ),'choose_pet_step1'
       );

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the historical dialogue (Context), the current query (Current Query), and the result generated from the previous prompt, use the following information to organize a response. First, recommend three pet breeds based on the current information. If additional information is needed, inquire about the required information from the user. Ensure the response is focused on pet selection, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Previous Prompt Result: PREVIOUSRESULT\n',
                'Rules:',
                '1. Focus on the topic of pet selection.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is helpful and supportive.\n',
                'Task: Based on the context, current query, and the previous prompt result, recommend three pet breeds most suitable according to the current information. If more information is needed, ask the user for the specific additional information required.'
        ), 'choose_pet_step2'
       );

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the user intent is to seek specific details about a particular type of pet, use the following historical dialogue (Context) and current query (Current Query) to provide the requested information. Determine if additional details are required from the user to provide accurate information. If additional details are needed, specify what information is required. Ensure the response is focused on providing pet details, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Rules:',
                '1. Focus on the topic of providing specific pet details.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is informative and supportive.\n',
                'Task: Based on the above context and current query, provide the requested details about the particular type of pet. Assess if additional details from the user are needed for more accurate information. If yes, specify the additional details required.'
        ), 'pet_information_step1'
       );

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the historical dialogue (Context), the current query (Current Query), and the result generated from the previous prompt, use the following information to organize a response. First, provide the requested specific details about the pet. If additional details are needed, inquire about the required information from the user. Ensure the response is focused on providing pet details, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Previous Prompt Result: PREVIOUSRESULT\n',
                'Rules:',
                '1. Focus on the topic of providing specific pet details.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is informative and supportive.\n',
                'Task: Based on the context, current query, and the previous prompt result, provide the specific details about the pet requested by the user. If more details are needed, ask the user for the specific additional information required.'
        ), 'pet_information_step2'
       );

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the user intent is to seek information on how to care for a specific type of pet, use the following historical dialogue (Context) and current query (Current Query) to provide the necessary pet care information. Determine if additional details are required from the user to provide accurate care information. If additional details are needed, specify what information is required. Ensure the response is focused on providing pet care information, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Rules:',
                '1. Focus on the topic of providing pet care information.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is informative and supportive.\n',
                'Task: Based on the above context and current query, provide the necessary information on how to care for the specific type of pet. Assess if additional details from the user are needed for more accurate care information. If yes, specify the additional details required.'
        ), 'pet_care_step1'
       );

INSERT INTO prompt_templates (template, prompt_code)
VALUES (
        CONCAT(
                'Given the historical dialogue (Context), the current query (Current Query), and the result generated from the previous prompt, use the following information to organize a response. First, provide the necessary information on how to care for the specific type of pet. If additional details are needed, inquire about the required information from the user. Ensure the response is focused on providing pet care information, avoiding any discriminatory, negative, or toxic content.\n',
                'Context: HISTORYQA\n',
                'Current Query: CURRENTQ\n',
                'Previous Prompt Result: PREVIOUSRESULT\n',
                'Rules:',
                '1. Focus on the topic of providing pet care information.\n',
                '2. Avoid generating any discriminatory, negative, or toxic content.\n',
                '3. Ensure the response is informative and supportive.\n',
                'Task: Based on the context, current query, and the previous prompt result, provide the necessary information on how to care for the specific type of pet. If more details are needed, ask the user for the specific additional information required.'
        ), 'pet_care_step2'
       );