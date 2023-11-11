package com.manager.taskapi.domain.user.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {

    private String name;
    private String email;

    public String getName() {
        return treatLikeString(name);
    }

    public String getEmail() {
        return treatLikeString(email);
    }

    private String treatLikeString(String text) {
        if (text == null)
            return null;
        return "%" + text.replace("?", "%").toLowerCase() + "%";
    }
}
