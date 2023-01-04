package com.momo2x.mbdn.graphql.exception;

public class MemberEndpointException extends GraphQlEndpointException {
    public MemberEndpointException() {
    }

    public MemberEndpointException(String message) {
        super(message);
    }

    public MemberEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public MemberEndpointException(Throwable cause) {
        super(cause);
    }

    public MemberEndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
