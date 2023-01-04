package com.momo2x.mbdn.graphql.config;

/**
 * Just an abstract template that all service settings wrappers must extend.
 */
public abstract class ServiceEndpointProperties {
    public abstract String getProtocol();
    public abstract String getHost();
    public abstract int getPort();
    public abstract String getUsername();
    public abstract String getPassword();

    public String url(final String endpoint) {
        return "%s://%s:%d/%s".formatted(getProtocol(), getHost(), getPort(), endpoint);
    }
}
