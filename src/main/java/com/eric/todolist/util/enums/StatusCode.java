package com.eric.todolist.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum StatusCode {
    OK(HttpStatus.OK, "success"),
    NO_CONTENT(HttpStatus.NO_CONTENT, "no content request"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "argument is not valid"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "credentials is not valid"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "protected"),
    CONFLICT(HttpStatus.CONFLICT, "conflict request"),
    INVALID_JSON_PAYLOAD(HttpStatus.BAD_REQUEST, "JSON is not valid"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "something wrong"),
    UNPROCESSABLE_ENTITY(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "data not found");

    private final HttpStatus httpStatus;
    private final String message;

}
