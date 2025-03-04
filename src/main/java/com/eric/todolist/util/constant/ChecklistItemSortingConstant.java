package com.eric.todolist.util.constant;

import java.util.Map;

public class ChecklistItemSortingConstant {
    private ChecklistItemSortingConstant() {}
    public static final Map<String, String> CHECKLIST_ITEMS_SORTING_FIELD = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("completed", "completed"),
            Map.entry("item_name", "itemName")
    );
}
