package com.eric.todolist.exception;

import com.eric.todolist.util.enums.StatusCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
public class GlobalException extends RuntimeException implements Serializable {

    final StatusCode statusCode;

    final String message;
}
