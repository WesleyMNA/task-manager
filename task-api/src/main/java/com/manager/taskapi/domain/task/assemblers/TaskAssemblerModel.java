package com.manager.taskapi.domain.task.assemblers;

import com.manager.taskapi.domain.task.Task;
import com.manager.taskapi.domain.task.TaskController;
import com.manager.taskapi.domain.task.dtos.TaskResponse;
import com.manager.taskapi.domain.task.dtos.TaskRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class TaskAssemblerModel implements RepresentationModelAssembler<Task, TaskResponse> {

    protected final ModelMapper mapper;

    @Override
    public @NotNull TaskResponse toModel(@NotNull Task task) {
        var response = mapper.map(task, TaskResponse.class);
        response.add(linkTo(methodOn(TaskController.class).findById(response.getId())).withRel("detail-task"));
        response.add(linkTo(methodOn(TaskController.class).update(response.getId(), new TaskRequest())).withRel("update-task"));
        response.add(linkTo(methodOn(TaskController.class).delete(response.getId())).withRel("delete-task"));
        return response;
    }
}
