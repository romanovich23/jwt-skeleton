package com.roman.jwtskeleton.security;

public class Constants {

    public static final String LOGIN_URL = "/login";
    public static final String HEADER_AUTH_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer";

    public static final String ISSUER_INFO = "https://github.com/romanovich23/jwt-skeleton";
    public static final String SUPER_SECRET_KEY = "mySecretKey";
    public static final long TOKEN_EXPIRATION_TIMEOUT = 7200000;

}
