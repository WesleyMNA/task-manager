package com.manager.taskapi.domain.task.assemblers;

import com.manager.taskapi.domain.note.NoteController;
import com.manager.taskapi.domain.note.dtos.NoteResponse;
import com.manager.taskapi.domain.note.services.NoteAssemblerModel;
import com.manager.taskapi.domain.task.Task;
import com.manager.taskapi.domain.task.TaskController;
import com.manager.taskapi.domain.task.dtos.DetailedTaskResponse;
import com.manager.taskapi.domain.task.dtos.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class DetailedTaskAssemblerModel implements RepresentationModelAssembler<Task, DetailedTaskResponse> {

    private final NoteAssemblerModel noteAssembler;
    private final ModelMapper mapper;

    @Override
    public @NotNull DetailedTaskResponse toModel(@NotNull Task task) {
        var response = mapper.map(task, DetailedTaskResponse.class);
        response.add(linkTo(methodOn(TaskController.class).update(response.getId(), new TaskRequest())).withRel("update-task"));
        response.add(linkTo(methodOn(TaskController.class).delete(response.getId())).withRel("delete-task"));
        response.add(linkTo(methodOn(NoteController.class).findAll(task.getId(), PageRequest.of(0, 20))).withRel("task-notes"));
        Set<NoteResponse> noteResponses = task.getNotes()
                .stream()
                .map(noteAssembler::toModel)
                .collect(Collectors.toSet());
        response.setNotes(noteResponses);
        return response;
    }
}
