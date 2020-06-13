package com.roman.jwtskeleton.error;

public class UserAlreadyExistsException extends RuntimeException {

    private String message;
    private String userName;

    public UserAlreadyExistsException(String userName) {
        this.userName = userName;
        this.message = "The user " + userName + " already exists";
    }

    public UserAlreadyExistsException(String message, String userName) {
        this.message = message;
        this.userName = userName;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getUserName() {
        return userName;
    }
}
