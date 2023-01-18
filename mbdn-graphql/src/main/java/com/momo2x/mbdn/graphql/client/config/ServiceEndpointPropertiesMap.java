package com.momo2x.mbdn.graphql.client.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ServiceEndpointPropertiesMap implements InitializingBean {

    private final Environment env;

    private Map<RemoteServiceType, ServiceEndpointProperties> properties;

    public ServiceEndpointProperties getProperties(final RemoteServiceType type) {
        return properties.get(type);
    }

    @Override
    public void afterPropertiesSet() {
        properties = new HashMap<>();

        for (RemoteServiceType type : RemoteServiceType.values()) {
            properties.put(type, new ServiceEndpointProperties(type, env));
        }
    }

}
