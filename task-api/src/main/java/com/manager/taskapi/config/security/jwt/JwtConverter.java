package com.manager.taskapi.config.security.jwt;

import com.nimbusds.jose.shaded.json.JSONObject;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class JwtConverter implements Converter<Jwt, JwtAuthentication> {

    private final ModelMapper mapper;

    @Override
    public JwtAuthentication convert(Jwt jwt) {
        JSONObject context = jwt.getClaim("context");
        UserJwt client = mapper.map(context, UserJwt.class);
        return new JwtAuthentication(List.of(Role.USER), jwt, client);
    }

    public enum Role implements GrantedAuthority {
        USER;

        @Override
        public String getAuthority() {
            return "ROLE_" + this.name();
        }

    }
}
