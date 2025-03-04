package com.eric.todolist.specification;

import com.eric.todolist.model.entity.Checklist;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ChecklistSpecification {

    public Specification<Checklist> searchUser(Integer id) {
        if (id == null) {
            return ((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        }

        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("id"), id));
    }

    public Specification<Checklist> searchName(String search) {
        if (search == null || search.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + search.toLowerCase() + "%");
    }
}
