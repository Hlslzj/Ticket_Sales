package com.edu.dyh.Exception;

import org.springframework.security.core.AuthenticationException;

public class UsernameIsExistedException extends AuthenticationException {
    public UsernameIsExistedException(String msg) {
        super(msg);
    }

    public UsernameIsExistedException(String msg, Throwable t) {
        super(msg, t);
    }
}
