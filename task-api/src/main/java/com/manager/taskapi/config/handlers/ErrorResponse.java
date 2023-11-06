package com.manager.taskapi.config.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(String error,
                            String message,
                            String path,
                            int status,
                            LocalDateTime timestamp,
                            @JsonInclude(JsonInclude.Include.NON_NULL) Map<String, String> errors) {
}
