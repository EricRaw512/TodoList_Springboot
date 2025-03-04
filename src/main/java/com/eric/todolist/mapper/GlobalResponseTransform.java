package com.eric.todolist.mapper;

import com.eric.todolist.model.dto.response.GlobalResponse;
import com.eric.todolist.util.enums.StatusCode;
import com.eric.todolist.util.transform.ConvertTransform;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = ConvertTransform.class)
public interface GlobalResponseTransform {

    @Named("generateResponse")
    @Mapping(target = "timestamp"  , source = "dateTime", qualifiedByName = "dateTimeToString")
    @Mapping(target = "code"  , expression = "java(statusCode.getHttpStatus().value())")
    @Mapping(target = "description"  , source = "statusCode")
    @Mapping(target = "message"  , source = "statusCode.message")
    @Mapping(target = "result", source = "result")
    GlobalResponse<Object> generateResponse(LocalDateTime dateTime, StatusCode statusCode, Object result);

    @Named("generateResponse")
    @Mapping(target = "timestamp"  , source = "dateTime", qualifiedByName = "dateTimeToString")
    @Mapping(target = "code"  , expression = "java(statusCode.getHttpStatus().value())")
    @Mapping(target = "description"  , source = "statusCode")
    @Mapping(target = "message"  , source = "message")
    @Mapping(target = "result", source = "result")
    GlobalResponse<Object> generateResponse(LocalDateTime dateTime, StatusCode statusCode, String message, Object result);
}
