package com.manager.taskapi.domain.note.services;

import com.manager.taskapi.domain.note.Note;
import com.manager.taskapi.domain.note.NoteController;
import com.manager.taskapi.domain.note.dtos.NoteRequest;
import com.manager.taskapi.domain.note.dtos.NoteResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class NoteAssemblerModel implements RepresentationModelAssembler<Note, NoteResponse> {

    private final ModelMapper mapper;

    @Override
    public @NotNull NoteResponse toModel(@NotNull Note note) {
        var response = mapper.map(note, NoteResponse.class);
        response.add(linkTo(methodOn(NoteController.class).update(response.getId(), new NoteRequest())).withRel("update-note"));
        response.add(linkTo(methodOn(NoteController.class).delete(response.getId())).withRel("delete-note"));
        return response;
    }
}
