package com.eric.todolist.model.dto.response;

import com.eric.todolist.util.enums.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class GlobalResponse<T> {

    protected String timestamp;

    @JsonProperty("code")
    protected Integer code;

    @JsonProperty("description")
    protected StatusCode description;

    @JsonProperty("message")
    protected String message;

    @JsonProperty("result")
    protected T result;

}
