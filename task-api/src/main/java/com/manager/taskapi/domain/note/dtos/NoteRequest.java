package com.manager.taskapi.domain.note.dtos;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class NoteRequest {

    @NotNull
    @NotBlank
    private String text;
    @NotNull
    private Long taskId;
}
