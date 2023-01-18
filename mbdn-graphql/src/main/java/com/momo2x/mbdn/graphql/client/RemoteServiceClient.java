package com.momo2x.mbdn.graphql.client;

import com.momo2x.mbdn.graphql.client.config.RemoteServiceType;
import com.momo2x.mbdn.graphql.client.config.ServiceEndpointProperties;
import com.momo2x.mbdn.graphql.client.config.ServiceEndpointPropertiesMap;
import com.momo2x.mbdn.graphql.client.exception.NoServiceForClientException;
import com.momo2x.mbdn.graphql.client.exception.RemoteServiceClientException;
import com.momo2x.mbdn.graphql.client.http.HttpHeadersFactory;
import com.momo2x.mbdn.graphql.client.http.RestTemplateFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

public abstract class RemoteServiceClient {

    private final RestTemplateFactory restTemplateFactory;

    private final HttpHeadersFactory httpHeadersFactory;

    private final ServiceEndpointPropertiesMap propertiesMap;

    public RemoteServiceClient(RestTemplateFactory restTemplateFactory, HttpHeadersFactory httpHeadersFactory, ServiceEndpointPropertiesMap propertiesMap) {
        this.restTemplateFactory = restTemplateFactory;
        this.httpHeadersFactory = httpHeadersFactory;
        this.propertiesMap = propertiesMap;
    }

    protected ServiceEndpointProperties getProperties() {
        return propertiesMap.getProperties(getServiceType());
    }

    protected RestTemplate getRestTemplate() {
        assert restTemplateFactory.getObject() != null;
        return restTemplateFactory.getObject().get(getServiceType());
    }

    protected HttpHeaders getHttpHeaders() {
        assert httpHeadersFactory.getObject() != null;

        try {
            return httpHeadersFactory.getObject().get(getServiceType());
        } catch (Exception e) {
            throw new RemoteServiceClientException(e);
        }
    }

    private RemoteServiceType getServiceType() {
        final var thisClass = this.getClass();

        if (!thisClass.isAnnotationPresent(RemoteServiceAdapter.class)) {
            throw new NoServiceForClientException(
                    "No %s annotation in %s".formatted(
                            RemoteServiceAdapter.class.getSimpleName(),
                            thisClass.getSimpleName()));
        }

        return thisClass.getAnnotation(RemoteServiceAdapter.class).value();
    }
}
