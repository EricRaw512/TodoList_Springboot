package com.eric.todolist.specification;

import com.eric.todolist.model.dto.request.ChecklistItemFilterRequest;
import com.eric.todolist.model.entity.ChecklistItem;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class ChecklistItemSpecification {

    public Specification<ChecklistItem> findByChecklistId(Integer checklistId) {
        if (checklistId == null) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.conjunction();
        }

        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("checklist").get("id"), checklistId);
    }

    public Specification<ChecklistItem> searchChecklistItem(ChecklistItemFilterRequest request) {
        return findByName(request.getItemName())
                .and(findByCompleted(request.isCompleted()));
    }

    private Specification<ChecklistItem> findByCompleted(Boolean completed) {
        if (completed == null) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
                cb.equal(root.get("completed"), completed);
    }

    private Specification<ChecklistItem> findByName(String itemName) {
        if (itemName == null || itemName.isBlank()) {
            return (root, query, cb) -> cb.conjunction();
        }

        return (root, query, cb) ->
                cb.like(cb.lower(root.get("itemName")), "%" + itemName.toLowerCase() + "%");
    }


}
