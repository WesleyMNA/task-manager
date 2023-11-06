package com.manager.taskapi.domain.task.dtos;

import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;

public record TaskQuery(
        String title,
        TaskPriority priority,
        TaskStatus status
) {
}
