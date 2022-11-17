package com.homihq.manager.exception;

import org.springframework.core.NestedRuntimeException;

public class NoDataFoundException extends NestedRuntimeException {
    public NoDataFoundException(String msg) {
        super(msg);
    }
}
