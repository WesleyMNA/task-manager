package com.manager.taskapi.domain.auth.services;


import com.manager.taskapi.config.security.jwt.UserJwt;
import com.manager.taskapi.config.security.jwt.JwtProperty;
import com.manager.taskapi.domain.auth.dtos.AuthResponse;
import com.manager.taskapi.domain.user.User;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


@AllArgsConstructor
@Service
public final class JwtService {

    private final JwtEncoder encoder;
    private final JwtProperty property;
    private final ModelMapper mapper;

    public void createToken(User client, AuthResponse response) {
        Instant now = Instant.now();
        Instant expiration = now.plus(property.lifetime(), ChronoUnit.MINUTES);
        UserJwt userJwt = mapper.map(client, UserJwt.class);

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("Fotosensores")
                .issuedAt(now)
                .expiresAt(expiration)
                .subject(String.valueOf(client.getId()))
                .claim("context", userJwt)
                .build();
        String jwt = encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        response.setJwt(jwt);
        response.setType("bearer");
    }
}
