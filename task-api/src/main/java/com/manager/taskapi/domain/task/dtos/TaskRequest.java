package com.manager.taskapi.domain.task.dtos;

import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class TaskRequest {

    @NotNull
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private TaskPriority priority;
    @NotNull
    private TaskStatus status;
    private LocalDate initialDate;
    private LocalDate finalDate;
    private Long clientId;
}
