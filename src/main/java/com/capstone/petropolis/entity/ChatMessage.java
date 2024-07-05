package com.capstone.petropolis.entity;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "chat_message")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Hidden
    private Long id;

    private Long chatId;

    private Long sessionId;

    private String content;

    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @Hidden
    private UserEntity user;

    private boolean isAnswer;

    private boolean isQuery;

    @ElementCollection
    @CollectionTable(name = "history_qa", joinColumns = @JoinColumn(name = "chat_message_id"))
    @Column(name = "qa")
    private List<String> historyQA;

}

