package com.manager.taskapi.domain.user.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "users")
public class UserResponse extends RepresentationModel<UserResponse> {

    private Long id;
    private String name;
    private String email;
}
