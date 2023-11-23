package com.manager.taskapi.domain.auth.services;


import com.manager.taskapi.domain.auth.AuthController;
import com.manager.taskapi.domain.auth.dtos.AuthResponse;
import com.manager.taskapi.domain.note.NoteController;
import com.manager.taskapi.domain.task.TaskController;
import com.manager.taskapi.domain.task.dtos.TaskQuery;
import com.manager.taskapi.domain.user.UserController;
import com.manager.taskapi.domain.user.dtos.UserQuery;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AuthModelAssembler implements RepresentationModelAssembler<AuthResponse, AuthResponse> {

    @Override
    public @NotNull AuthResponse toModel(@NotNull AuthResponse response) {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        PageRequest page = PageRequest.of(0, 20);
        response.add(linkTo(methodOn(AuthController.class).refresh(null)).withRel("refresh-token"));
        // ClientController
        response.add(linkTo(methodOn(UserController.class).findAll(new UserQuery(null, null), page)).withRel("clients"));
        // TaskController
        response.add(linkTo(methodOn(TaskController.class).findAll(new TaskQuery(null, null, null), page)).withRel("tasks"));
        // NoteController
        response.add(linkTo(methodOn(NoteController.class).findAll(null, page)).withRel("notes"));
        return response;
    }
}
