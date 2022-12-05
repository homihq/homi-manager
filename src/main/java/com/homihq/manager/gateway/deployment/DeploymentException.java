package com.homihq.manager.gateway.deployment;

import org.springframework.core.NestedRuntimeException;

public class DeploymentException extends NestedRuntimeException {
    public DeploymentException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
