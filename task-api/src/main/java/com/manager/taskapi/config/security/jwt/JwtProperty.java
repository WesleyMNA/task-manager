package com.manager.taskapi.config.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@ConfigurationProperties(prefix = "task-manager.auth.jwt")
public record JwtProperty(RSAPublicKey publicKey, RSAPrivateKey privateKey, Integer lifetime) {
}
