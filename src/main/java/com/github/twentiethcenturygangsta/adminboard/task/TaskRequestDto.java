package com.github.twentiethcenturygangsta.adminboard.task;

import com.github.twentiethcenturygangsta.adminboard.user.AdminBoardUser;
import lombok.Getter;

@Getter
public class TaskRequestDto {
    private final String content;

    public TaskRequestDto(String content) {
        this.content = content;
    }

    public Task toEntity(TaskRequestDto taskRequestDto, AdminBoardUser adminBoardUser) {
        return Task.builder()
                .content(taskRequestDto.getContent())
                .isCompleted(false)
                .adminBoardUser(adminBoardUser)
                .build();
    }
}
