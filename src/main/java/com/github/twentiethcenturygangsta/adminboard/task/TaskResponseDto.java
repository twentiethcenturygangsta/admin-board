package com.github.twentiethcenturygangsta.adminboard.task;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TaskResponseDto {
    private Long id;
    private String content;
    private Boolean isCompleted;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.content = task.getContent();
        this.isCompleted = task.getIsCompleted();
    }
}
