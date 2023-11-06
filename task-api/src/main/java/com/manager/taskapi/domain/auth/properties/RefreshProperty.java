package com.manager.taskapi.domain.auth.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "task-manager.auth.refresh")
public record RefreshProperty(Integer lifetime) {
}
