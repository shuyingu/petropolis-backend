package com.capstone.petropolis.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    private String chatId;

    private String content;

    @JsonProperty("isAnswer")
    private boolean isAnswer;

    @ElementCollection
    @CollectionTable(name = "history_qa", joinColumns = @JoinColumn(name = "chat_message_id"))
    @Column(name = "qa")
    private List<String> historyQA;

}

