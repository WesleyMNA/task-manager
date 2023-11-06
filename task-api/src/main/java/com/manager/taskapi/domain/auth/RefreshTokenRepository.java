package com.manager.taskapi.domain.auth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    void deleteAllByUserId(Long id);

    Optional<RefreshToken> findByToken(String token);

    List<RefreshToken> findByUserId(Long id);
}
