package com.manager.taskapi.domain.user.services;

import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.user.UserController;
import com.manager.taskapi.domain.user.dtos.UserResponse;
import com.manager.taskapi.domain.user.dtos.requests.UpdateUserRequest;
import com.manager.taskapi.domain.user.dtos.requests.UpdatePasswordRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@Component
public class UserAssemblerModel implements RepresentationModelAssembler<User, UserResponse> {

    private final ModelMapper mapper;

    @Override
    public @NotNull UserResponse toModel(@NotNull User client) {
        var response = mapper.map(client, UserResponse.class);
        response.add(linkTo(methodOn(UserController.class).findById(response.getId())).withRel("detail-user"));
        response.add(linkTo(methodOn(UserController.class).update(response.getId(), new UpdateUserRequest())).withRel("update-user"));
        response.add(linkTo(methodOn(UserController.class).updatePassword(response.getId(), new UpdatePasswordRequest())).withRel("update-password"));
        return response;
    }
}
