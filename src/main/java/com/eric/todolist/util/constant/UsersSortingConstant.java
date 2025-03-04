package com.eric.todolist.util.constant;

import java.util.Map;

public class UsersSortingConstant {
    private UsersSortingConstant(){}
    public static final Map<String, String> USERS_SORTING_FIELD = Map.ofEntries(
            Map.entry("id", "id"),
            Map.entry("username", "username"),
            Map.entry("role", "role")
    );
}
