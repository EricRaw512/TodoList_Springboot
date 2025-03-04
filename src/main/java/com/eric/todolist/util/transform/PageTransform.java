package com.eric.todolist.util.transform;

import com.eric.todolist.model.dto.response.PageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PageTransform {

    @Named("toPageDto")
    @Mapping(target = "currentPage", source = "number", qualifiedByName = "toCustomNumberPage")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "offsetElements", source = "offsetElements")
    @Mapping(target = "totalElementsPerPage", source = "totalElementsPerPage")
    @Mapping(target = "totalPages", source = "totalPages")
    @Mapping(target = "content", source = "content")
    PageResponse<List> toPage(
            Integer number,
            Long totalElements,
            Long offsetElements,
            Integer totalElementsPerPage,
            Integer totalPages,
            List content);

    @Named("toCustomNumberPage")
    default Integer toCustomNumberPage(Integer number) {
        return number + 1;
    }
}
