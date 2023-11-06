package com.manager.taskapi.domain.user;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public Specification<User> nameLikeIfNotNull(String name) {
        return name != null ? (root, query, builder) -> builder.like(root.get("name"), name) : alwaysTrue();
    }

    public Specification<User> emailLikeIfNotNull(String email) {
        return email != null ? (root, query, builder) -> builder.like(root.get("email"), email) : alwaysTrue();
    }

    private Specification<User> alwaysTrue() {
        return (root, query, builder) -> builder.and();
    }
}
