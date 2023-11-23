package com.manager.taskapi.domain.note;

import com.manager.taskapi.domain.note.dtos.NoteRequest;
import com.manager.taskapi.domain.note.dtos.NoteResponse;
import com.manager.taskapi.domain.note.services.NoteService;
import com.manager.taskapi.domain.task.dtos.TaskResponse;
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
@RequestMapping("/v1/notes")
public class NoteController {

    private final NoteService service;

    @GetMapping
    public ResponseEntity<PagedModel<NoteResponse>> findAll(@RequestParam @ParameterObject Long taskId,
                                                            @ParameterObject Pageable pageable) {
        PagedModel<NoteResponse> response = service.findAll(taskId, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<NoteResponse> create(@RequestBody @Valid NoteRequest request,
                                               UriComponentsBuilder builder) {
        NoteResponse response = service.create(request);
        URI uri = builder.path("/v1/notes/{id}").buildAndExpand(response.getId()).toUri();
        return ResponseEntity
                .created(uri)
                .body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid NoteRequest request) {
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
