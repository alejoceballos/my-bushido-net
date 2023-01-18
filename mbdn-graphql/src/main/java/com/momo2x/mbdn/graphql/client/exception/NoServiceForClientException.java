package com.momo2x.mbdn.graphql.client.exception;

import com.momo2x.mbdn.graphql.exception.GraphQlEndpointException;

public class NoServiceForClientException extends GraphQlEndpointException {

    public NoServiceForClientException(String message) {
        super(message);
    }

}
