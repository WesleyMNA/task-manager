package com.manager.taskapi.domain.user;

import com.manager.taskapi.domain.user.dtos.UserQuery;
import com.manager.taskapi.domain.user.dtos.UserResponse;
import com.manager.taskapi.domain.user.dtos.requests.UpdateUserRequest;
import com.manager.taskapi.domain.user.dtos.requests.UpdatePasswordRequest;
import com.manager.taskapi.domain.user.dtos.requests.UserRequest;
import com.manager.taskapi.domain.user.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService service;

    @GetMapping
    public ResponseEntity<PagedModel<UserResponse>> findAll(@ParameterObject UserQuery query,
                                                            @ParameterObject Pageable pageable) {
        PagedModel<UserResponse> response = service.findAll(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        UserResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@RequestBody @Valid UserRequest request,
                                               UriComponentsBuilder builder) {
        UserResponse response = service.create(request);
        URI uri = builder.path("/v1/users/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid UpdateUserRequest request) {
        service.update(id, request);
        return ResponseEntity
                .noContent()
                .build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id,
                                               @RequestBody @Valid UpdatePasswordRequest request) {
        service.updatePassword(id, request);
        return ResponseEntity
                .noContent()
                .build();
    }
}
