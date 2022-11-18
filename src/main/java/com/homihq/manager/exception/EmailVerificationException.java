package com.homihq.manager.exception;

import org.springframework.core.NestedRuntimeException;

public class EmailVerificationException extends NestedRuntimeException {
    public EmailVerificationException(String msg) {
        super(msg);
    }
}
