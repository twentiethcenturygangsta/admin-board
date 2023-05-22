package com.github.twentiethcenturygangsta.adminboard.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task {

    @Id
    @GeneratedValue
    @Column(name="task_id")
    private Long id;
    private String content;
    private Boolean isCompleted;

    @Builder
    public Task(String content, Boolean isCompleted) {
        this.content = content;
        this.isCompleted = isCompleted;
    }
}
