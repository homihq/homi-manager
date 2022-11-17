package com.homihq.manager.exception;

import org.springframework.core.NestedRuntimeException;

public class EmailLinkVerificationFailureException extends NestedRuntimeException {
    public EmailLinkVerificationFailureException(String msg) {
        super(msg);
    }
}
