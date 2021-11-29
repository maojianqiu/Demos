package org.vae.exception;

import org.apache.shiro.authc.AuthenticationException;

public class BugException extends AuthenticationException {
    public BugException(String meg) {
        super(meg);
    }
}
