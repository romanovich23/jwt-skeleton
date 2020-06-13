package com.roman.jwtskeleton.error;

public class UserNotFoundException extends RuntimeException {

    private String message;
    private String userName;

    public UserNotFoundException(String userName) {
        this.userName = userName;
        this.message = "User " + userName + " was not found";
    }

    public UserNotFoundException(String message, String userName) {
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
