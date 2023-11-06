package com.manager.taskapi.domain.root;

import com.manager.taskapi.domain.auth.AuthController;
import com.manager.taskapi.domain.auth.dtos.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class RootEntryPointController {

    @GetMapping
    public ResponseEntity<RootEntryPointResponse> getRoot() {
        RootEntryPointResponse response = new RootEntryPointResponse();
        response.add(linkTo(methodOn(AuthController.class).login(new LoginRequest(null, null))).withRel("login"));
        response.add(linkTo(methodOn(AuthController.class).refresh(null)).withRel("refresh-token"));
        return ResponseEntity.ok(response);
    }
}
