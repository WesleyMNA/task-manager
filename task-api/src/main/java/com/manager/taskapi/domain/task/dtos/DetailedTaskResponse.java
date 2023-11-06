package com.manager.taskapi.domain.task.dtos;

import com.manager.taskapi.domain.note.dtos.NoteResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
public class DetailedTaskResponse extends TaskResponse {

    private String description;
    private LocalDate finalDate;
    private Set<NoteResponse> notes;
}
