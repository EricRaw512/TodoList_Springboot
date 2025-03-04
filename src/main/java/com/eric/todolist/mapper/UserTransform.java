package com.eric.todolist.mapper;

import com.eric.todolist.model.dto.response.UserResponse;
import com.eric.todolist.model.entity.Users;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTransform {

     @Named("toUserResponseList")
     @IterableMapping(qualifiedByName = "toUserResponse")
     List<UserResponse> toUserResponseList(List<Users> allUsers);

     @Named("toUserResponse")
     UserResponse toUserResponse(Users updatedUser);
}
