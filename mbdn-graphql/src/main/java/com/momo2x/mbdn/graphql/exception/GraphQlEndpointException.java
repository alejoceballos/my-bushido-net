package com.momo2x.mbdn.graphql.exception;

public class GraphQlEndpointException extends RuntimeException {
    public GraphQlEndpointException() {
    }

    public GraphQlEndpointException(String message) {
        super(message);
    }

    public GraphQlEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphQlEndpointException(Throwable cause) {
        super(cause);
    }

    public GraphQlEndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
