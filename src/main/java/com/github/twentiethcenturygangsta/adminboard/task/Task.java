package com.github.twentiethcenturygangsta.adminboard.task;

import com.github.twentiethcenturygangsta.adminboard.annotation.AdminBoardEntity;
import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_board_user_id")
    private AdminBoardUser adminBoardUser;

    @Builder
    public Task(String content, Boolean isCompleted, AdminBoardUser adminBoardUser) {
        this.content = content;
        this.isCompleted = isCompleted;
        this.adminBoardUser = adminBoardUser;
    }

    public void checkIsCompleted() {
        this.isCompleted = !isCompleted;
    }
}
