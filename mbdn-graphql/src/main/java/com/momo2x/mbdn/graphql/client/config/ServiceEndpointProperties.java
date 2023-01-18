package com.momo2x.mbdn.graphql.client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
public class ServiceEndpointProperties {

    private static final String PATH = "com.momo2x.mbdn.%s.%s";

    private final RemoteServiceType type;
    private final Environment env;

    public String getProtocol() {
        return getProperty("protocol");
    }

    public String getHost() {
        return getProperty("host");
    }

    public int getPort() {
        return Integer.parseInt(getProperty("port"));
    }

    public String getUsername() {
        return getProperty("username");
    }

    public String getPassword() {
        return getProperty("password");
    }

    public String getProperty(final String property) {
        return env.getProperty(getPath(property));
    }

    public String url(final String property, Object... parameters) {
        final var endpoint = getEndpoint(property);
        final var parameterizedEndpoint = endpoint.formatted(parameters);
        return "%s://%s:%d/%s".formatted(getProtocol(), getHost(), getPort(), parameterizedEndpoint);
    }

    private String getPath(final String property) {
        return PATH.formatted(
                type.name().toLowerCase(),
                property);
    }

    private String getEndpoint(final String property) {
        return env.getProperty(getPath("api." + property));
    }

}
