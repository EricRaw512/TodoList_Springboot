package com.eric.todolist.mapper;

import com.eric.todolist.model.dto.response.ChecklistResponse;
import com.eric.todolist.model.entity.Checklist;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChecklistTransform {

    @Named("toChecklistResponse")
    ChecklistResponse toChecklistResponse(Checklist checklist);

    @Named("toChecklistResponses")
    @IterableMapping(qualifiedByName = "toChecklistResponse")
    List<ChecklistResponse> toChecklistResponses(List<Checklist> checklists);
}
