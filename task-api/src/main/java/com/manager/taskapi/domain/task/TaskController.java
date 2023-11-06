package com.manager.taskapi.domain.task;

import com.manager.taskapi.domain.task.dtos.DetailedTaskResponse;
import com.manager.taskapi.domain.task.dtos.TaskQuery;
import com.manager.taskapi.domain.task.dtos.TaskResponse;
import com.manager.taskapi.domain.task.dtos.TaskRequest;
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
@RequestMapping("/v1/tasks")
public class TaskController {

    private final TaskService service;

    @GetMapping
    public ResponseEntity<PagedModel<TaskResponse>> findAll(@ParameterObject TaskQuery query,
                                                            @ParameterObject Pageable pageable) {
        PagedModel<TaskResponse> response = service.findAll(query, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DetailedTaskResponse> findById(@PathVariable Long id) {
        DetailedTaskResponse response = service.findById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TaskResponse> create(@RequestBody @Valid TaskRequest request,
                                               UriComponentsBuilder builder) {
        TaskResponse response = service.create(request);
        URI uri = builder.path("/v1/tasks/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid TaskRequest request) {
        service.update(id, request);
        return ResponseEntity
                .noContent()
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity
                .noContent()
                .build();
    }
}
