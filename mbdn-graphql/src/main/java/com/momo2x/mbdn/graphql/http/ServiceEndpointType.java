package com.momo2x.mbdn.graphql.http;

public enum ServiceEndpointType {
    MEMBERS("members");

    private String serviceId;

    ServiceEndpointType(String serviceId) {
        this.serviceId = serviceId;
    }
}
