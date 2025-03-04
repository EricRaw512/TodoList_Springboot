package com.eric.todolist.config.exception;

import com.eric.todolist.exception.GlobalException;
import com.eric.todolist.exception.InvalidCredentialsException;
import com.eric.todolist.exception.UserAlreadyExistException;
import com.eric.todolist.model.dto.response.GlobalResponse;
import com.eric.todolist.util.enums.StatusCode;
import com.eric.todolist.util.transform.ConvertTransform;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.eric.todolist.util.enums.StatusCode.UNPROCESSABLE_ENTITY;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionConfiguration extends ResponseEntityExceptionHandler {

    private final ConvertTransform convertTransform;

    @ExceptionHandler(GlobalException.class)
    protected ResponseEntity<Object> globalCustomException(GlobalException ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                convertTransform.getTimestamp(LocalDateTime.now()),
                ex.getStatusCode().getHttpStatus().value(),
                ex.getStatusCode(),
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, new HttpHeaders(), ex.getStatusCode().getHttpStatus());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Object> handleInvalidCredentials(InvalidCredentialsException ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                convertTransform.getTimestamp(LocalDateTime.now()),
                StatusCode.UNAUTHORIZED.getHttpStatus().value(),
                StatusCode.UNAUTHORIZED,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, new HttpHeaders(), StatusCode.UNAUTHORIZED.getHttpStatus());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<Object> handleUserAlreadyExist(UserAlreadyExistException ex) {
        GlobalResponse<Object> response = new GlobalResponse<>(
                convertTransform.getTimestamp(LocalDateTime.now()),
                StatusCode.CONFLICT.getHttpStatus().value(),
                StatusCode.CONFLICT,
                ex.getMessage(),
                null
        );

        return new ResponseEntity<>(response, new HttpHeaders(), StatusCode.CONFLICT.getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<Object> errors = new ArrayList<>();
        String message = null;

        for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
            if (message == null) {
                message = error.getDefaultMessage();
            }

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("message", error.getDefaultMessage());
            errors.add(errorMap);
        }

        for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            if (message == null) {
                message = error.getDefaultMessage();
            }

            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("object", error.getObjectName());
            errorMap.put("message", error.getDefaultMessage());
            errors.add(errorMap);
        }

        GlobalResponse<List<Object>> response = new GlobalResponse<>(
                convertTransform.getTimestamp(LocalDateTime.now()),
                UNPROCESSABLE_ENTITY.getHttpStatus().value(),
                UNPROCESSABLE_ENTITY,
                message,
                errors);

        return new ResponseEntity<>(response, headers, UNPROCESSABLE_ENTITY.getHttpStatus());
    }
}
