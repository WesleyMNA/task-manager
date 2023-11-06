package com.manager.taskapi.domain.note.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class NoteResponse extends RepresentationModel<NoteResponse> {

    private Long id;
    private String text;
    private LocalDateTime dateHour;
}
