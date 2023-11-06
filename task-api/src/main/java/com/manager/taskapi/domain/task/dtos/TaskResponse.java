package com.manager.taskapi.domain.task.dtos;

import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class TaskResponse extends RepresentationModel<TaskResponse> {

    private Long id;
    private String title;
    private TaskPriority priority;
    private TaskStatus status;
    private LocalDate initialDate;
}
