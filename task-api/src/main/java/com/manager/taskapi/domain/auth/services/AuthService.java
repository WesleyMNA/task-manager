package com.manager.taskapi.domain.auth.services;

import com.manager.taskapi.config.handlers.exceptions.UnauthorizedException;
import com.manager.taskapi.domain.auth.RefreshToken;
import com.manager.taskapi.domain.auth.dtos.AuthResponse;
import com.manager.taskapi.domain.auth.dtos.LoginRequest;
import com.manager.taskapi.domain.user.User;
import com.manager.taskapi.domain.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshService;
    private final AuthModelAssembler assembler;
    private final PasswordEncoder encoder;

    public AuthResponse login(LoginRequest request) {
        User user = validateRequest(request);
        refreshService.deleteTokensByUserId(user.getId());
        AuthResponse response = new AuthResponse();
        jwtService.createToken(user, response);
        refreshService.createToken(user, response);
        return assembler.toModel(response);
    }

    public AuthResponse refresh(HttpServletRequest request) {
        RefreshToken oldRefreshToken = refreshService.validateToken(request);
        AuthResponse response = new AuthResponse();
        jwtService.createToken(oldRefreshToken.getUser(), response);
        refreshService.createToken(oldRefreshToken, response);
        return assembler.toModel(response);
    }

    private User validateRequest(LoginRequest request) {
        Optional<User> optional = userRepository.findByEmail(request.email());

        if (optional.isEmpty() || !encoder.matches(request.password(), optional.get().getPassword()))
            throw new UnauthorizedException("email or password invalid");

        return optional.get();
    }
}
