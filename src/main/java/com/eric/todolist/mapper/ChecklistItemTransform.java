package com.eric.todolist.mapper;

import com.eric.todolist.model.dto.response.ChecklistItemResponse;
import com.eric.todolist.model.entity.ChecklistItem;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChecklistItemTransform {

    @Named("toChecklistItemResponse")
    ChecklistItemResponse toChecklistItemResponse(ChecklistItem content);

    @Named("toChecklistItemResponses")
    @IterableMapping(qualifiedByName = "toChecklistItemResponse")
    List<ChecklistItemResponse> toChecklistItemResponses(List<ChecklistItem> content);
}
