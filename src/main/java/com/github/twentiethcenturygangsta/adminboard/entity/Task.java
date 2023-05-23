package com.github.twentiethcenturygangsta.adminboard.entity;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardColumn;
import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Entity
@Getter
@AdminBoardEntity(group = "AdminBoard", description = "대시보드 사용자의 Task 등록 노트 테이블입니다.")
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
