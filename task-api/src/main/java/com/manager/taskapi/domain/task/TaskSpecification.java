package com.manager.taskapi.domain.task;

import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskPriority;
import com.manager.taskapi.domain.task.dtos.enumaration.TaskStatus;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;

public class TaskSpecification {

    public Specification<Task> titleLikeIfNotNull(String title) {
        return title != null ? (root, query, builder) -> builder.like(builder.lower(root.get("title")), title) : alwaysTrue();
    }

    public Specification<Task> priorityEqualsIfNotNull(TaskPriority priority) {
        return priority != null ? (root, query, builder) -> builder.equal(root.get("priority"), priority) : alwaysTrue();
    }

    public Specification<Task> statusEqualsIfNotNull(TaskStatus status) {
        return status != null ? (root, query, builder) -> builder.equal(root.get("status"), status) : alwaysTrue();
    }

    public Specification<Task> userRegisteredInTask(Long userId) {
        return (root, query, criteriaBuilder) -> {
            Join<User, Task> clientsTasks = root.join("user");
            return criteriaBuilder.equal(clientsTasks.get("id"), userId);
        };
    }

    private Specification<Task> alwaysTrue() {
        return (root, query, builder) -> builder.and();
    }
}
