package com.manager.taskapi.domain.note;

import org.springframework.data.jpa.domain.Specification;

public class NoteSpecification {

    private Specification<Note> alwaysTrue() {
        return (root, query, builder) -> builder.and();
    }

    public Specification<Note> taskIdIfNotNull(Long taskId) {
        return taskId != null ? (root, query, builder) -> builder.equal(root.get("task").get("id"), taskId) : alwaysTrue();
    }
}
