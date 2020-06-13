package com.roman.jwtskeleton.error;

public class NullParametersException extends NullPointerException {

    public NullParametersException() {
    }

    public NullParametersException(String message) {
        super(message);
    }
}
