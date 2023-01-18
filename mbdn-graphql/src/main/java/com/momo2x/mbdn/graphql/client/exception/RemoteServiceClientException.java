package com.momo2x.mbdn.graphql.client.exception;

import com.momo2x.mbdn.graphql.exception.GraphQlEndpointException;

public class RemoteServiceClientException extends GraphQlEndpointException {

    public RemoteServiceClientException(Throwable cause) {
        super(cause);
    }

}
