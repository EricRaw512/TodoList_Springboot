package com.eric.todolist.util.transform;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface ConvertTransform {

    String FORMAT_TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    @Named("dateTimeToString")
    default String getTimestamp(LocalDateTime date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_TIMESTAMP);
        return date.format(formatter);
    }
}
