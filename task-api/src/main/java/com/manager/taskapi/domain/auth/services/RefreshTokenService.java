package com.manager.taskapi.domain.auth.services;


import com.manager.taskapi.config.handlers.exceptions.UnauthorizedException;
import com.manager.taskapi.domain.auth.RefreshToken;
import com.manager.taskapi.domain.auth.RefreshTokenRepository;
import com.manager.taskapi.domain.auth.dtos.AuthResponse;
import com.manager.taskapi.domain.auth.properties.RefreshProperty;
import com.manager.taskapi.domain.user.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository repository;
    private final RefreshProperty refreshProperty;

    public RefreshToken createToken(User user, AuthResponse response) {
        String token = UUID.randomUUID().toString();
        response.setRefreshToken(token);
        LocalDateTime expirationDate = LocalDateTime.now().plusMinutes(refreshProperty.lifetime());
        RefreshToken refreshToken = new RefreshToken(token, user, expirationDate);
        return repository.save(refreshToken);
    }

    public void createToken(RefreshToken oldRefreshToken, AuthResponse response) {
        User user = oldRefreshToken.getUser();
        RefreshToken newRefreshToken = createToken(user, response);
        oldRefreshToken.setValid(false);
        oldRefreshToken.setFilho(newRefreshToken);
        repository.save(oldRefreshToken);
    }

    public RefreshToken validateToken(HttpServletRequest request) {
        String token = getToken(request)
                .orElseThrow(this::invalidToken);
        RefreshToken refreshToken = repository
                .findByToken(token)
                .orElseThrow(this::invalidToken);
        LocalDateTime now = LocalDateTime.now();

        if (!refreshToken.getValid() || refreshToken.getExpirationDatetime().isBefore(now)) {
            List<RefreshToken> refreshTokens = repository.findByUserId(refreshToken.getUser().getId());
            refreshTokens.forEach(r -> r.setValid(false));
            repository.saveAll(refreshTokens);
            throw this.invalidToken();
        }

        return refreshToken;
    }

    @Transactional
    public void deleteTokensByUserId(Long userId) {
        repository.deleteAllByUserId(userId);
    }

    private Optional<String> getToken(HttpServletRequest request) {
        String jwt = request.getHeader("refresh");
        return jwt == null ? Optional.empty() : Optional.of(jwt);
    }

    private UnauthorizedException invalidToken() {
        return new UnauthorizedException("Invalid token");
    }
}
