package com.manager.taskapi.domain.root;

import com.manager.taskapi.domain.auth.AuthController;
import com.manager.taskapi.domain.auth.dtos.LoginRequest;
import com.manager.taskapi.domain.user.UserController;
import com.manager.taskapi.domain.user.dtos.requests.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class RootEntryPointController {

    @GetMapping
    public ResponseEntity<RootEntryPointResponse> getRoot() {
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.newInstance();
        RootEntryPointResponse response = new RootEntryPointResponse();
        response.add(linkTo(methodOn(AuthController.class).login(new LoginRequest(null, null))).withRel("login"));
        response.add(linkTo(methodOn(UserController.class).create(new UserRequest(), uriComponentsBuilder)).withRel("register"));
        return ResponseEntity.ok(response);
    }
}
