package com.manager.taskapi.domain.note;

import com.manager.taskapi.domain.note.dtos.NoteRequest;
import com.manager.taskapi.domain.note.dtos.NoteResponse;
import com.manager.taskapi.domain.note.services.NoteService;
import lombok.RequiredArgsConstructor;
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
