package com.manager.taskapi.config.security.jwt;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;

@Data
@NoArgsConstructor
public class UserJwt {

    private Long id;
    private String name;
    private String email;

    public UserJwt(Jwt jwt) {
        JSONObject context = (JSONObject) jwt.getClaims().get("context");
        this.id = (Long) context.get("id");
        this.name = (String) context.get("name");
        this.email = (String) context.get("email");
    }
}
