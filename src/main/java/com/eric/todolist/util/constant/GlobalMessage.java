package com.eric.todolist.util.constant;

public class GlobalMessage {

    private GlobalMessage(){}

    public static class ExceptionMessage {
        private ExceptionMessage() {}
        public static final String USER_ALREADY_EXIST = "Username Already Exist";
        public static final String USERNAME_BLANK = "username cannot be blank";
        public static final String PASSWORD_BLANK = "password cannot be blank";
        public static final String USERNAME_OR_PASSWORD_WRONG = "Username or password is wrong";
        public static final String USER_NOT_FOUND = "User not found";
        public static final String CHECKLIST_NOT_FOUND = "Checklist id %d not found";
        public static final String USER_NOT_MATCH = "User doesn't match with Checklist user";
        public static final String NAME_BLANK = "Name should not be blank";
        public static final String CHECKLIST_ITEM_NOT_FOUND = "Checklist item id %d not found";
        public static final String CHECKLIST_NOT_MATCH_WITH_THE_USER = "Checklist doesn't match with the user";
        public static final String USER_NOT_EXIST = "User id %d not found";
    }

    public static class Response {
        private Response(){}
        public static class Checklist {
            private Checklist(){}
            public static final String CREATE_CHECKLIST = "Checklist successfully created";
            public static final String DELETE_CHECKLIST = "Checklist successfully deleted";
        }

        public static class ChecklistItem {
            private ChecklistItem(){}
            public static final String CREATE_CHECKLIST_ITEM = "Checklist item successfully created";
            public static final String DELETE_CHECKLIST_ITEM = "Checklist item successfully deleted";
        }

        public static class Authentication {
            private Authentication(){}
            public static final String CREATE_USER = "User successfully created";
        }
    }

}
