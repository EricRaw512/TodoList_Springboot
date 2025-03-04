package com.eric.todolist.util.constant;


import java.util.Map;

public class ChecklistSortingConstant {
    private ChecklistSortingConstant(){}
    public static final Map<String, String> CHECKLIST_SORTING_FIELD = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("name", "name")
    );
}

