package com.manager.taskapi.domain.task.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedTaskResponse extends TaskResponse {

    private String description;
    private LocalDate finalDate;
}
