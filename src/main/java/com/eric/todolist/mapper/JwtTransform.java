package com.eric.todolist.mapper;

import com.eric.todolist.model.dto.response.JwtResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface JwtTransform {

    @Named("toJwtResponse")
    JwtResponse toJwtResponse(String token);
}
