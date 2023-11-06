package com.manager.taskapi.config.handlers;


import com.manager.taskapi.config.handlers.exceptions.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @NotNull ResponseEntity<Object> handleMethodArgumentNotValid(@NotNull MethodArgumentNotValidException ex,
                                                                           @NotNull HttpHeaders headers,
                                                                           @NotNull HttpStatus status,
                                                                           @NotNull WebRequest request) {
        Map<String, String> errors = getErrors(ex);
        ErrorResponse errorResponse = getBaseErrorResponse((ServletWebRequest) request, errors, status);
        return handleExceptionInternal(
                ex, errorResponse, headers, status, request);
    }

    @Override
    protected @NotNull ResponseEntity<Object> handleBindException(@NotNull BindException ex,
                                                                  @NotNull HttpHeaders headers,
                                                                  @NotNull HttpStatus status,
                                                                  @NotNull WebRequest request) {
        Map<String, String> errors = getErrors(ex);
        ErrorResponse errorResponse = getBaseErrorResponse((ServletWebRequest) request, errors, status);
        return handleExceptionInternal(
                ex, errorResponse, headers, status, request);
    }

    @ExceptionHandler(UnauthorizedException.class)
    protected ResponseEntity<ErrorResponse> handleUnauthorizedException(Exception e,
                                                                        WebRequest request) {
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ErrorResponse errorResponse = new ErrorResponse(
                unauthorized.getReasonPhrase(),
                e.getMessage(),
                ((ServletWebRequest) request).getRequest().getRequestURI(),
                unauthorized.value(),
                LocalDateTime.now(),
                null
        );
        return ResponseEntity
                .status(errorResponse.status())
                .body(errorResponse);
    }

    @NotNull
    private ErrorResponse getBaseErrorResponse(ServletWebRequest request,
                                               Map<String, String> errors,
                                               HttpStatus status) {
        return new ErrorResponse(
                status.getReasonPhrase(),
                "Could not validate the request",
                request.getRequest().getRequestURI(),
                status.value(),
                LocalDateTime.now(),
                errors
        );
    }

    private Map<String, String> getErrors(@NotNull BindException ex) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();

        for (ObjectError allError : allErrors)
            if (!(allError instanceof FieldError))
                errors.put("request error %s".formatted(errors.size() + 1), allError.getDefaultMessage());

        for (FieldError error : ex.getBindingResult().getFieldErrors())
            errors.put(error.getField(), error.getDefaultMessage());

        return !errors.isEmpty() ? errors : null;
    }
}
