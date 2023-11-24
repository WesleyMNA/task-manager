package com.manager.taskapi.domain.note.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRequest {

    @NotNull
    @NotBlank
    private String text;
    @NotNull
    private Long taskId;
}
