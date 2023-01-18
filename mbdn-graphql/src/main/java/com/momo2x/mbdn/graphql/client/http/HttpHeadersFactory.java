package com.momo2x.mbdn.graphql.client.http;

import com.momo2x.mbdn.graphql.client.config.RemoteServiceType;
import com.momo2x.mbdn.graphql.client.config.ServiceEndpointPropertiesMap;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
@RequiredArgsConstructor
public class HttpHeadersFactory implements FactoryBean<Map<RemoteServiceType, HttpHeaders>>, InitializingBean {

    private final ServiceEndpointPropertiesMap propertiesMap;

    private Map<RemoteServiceType, HttpHeaders> httpHeadersMap;

    @Override
    public Map<RemoteServiceType, HttpHeaders> getObject() {
        return httpHeadersMap;
    }

    @Override
    public Class<HttpHeaders> getObjectType() {
        return HttpHeaders.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() {
        httpHeadersMap = new HashMap<>();

        for (RemoteServiceType type : RemoteServiceType.values()) {
            httpHeadersMap.put(
                    type,
                    createHeaders(
                            propertiesMap.getProperties(type).getUsername(),
                            propertiesMap.getProperties(type).getPassword()));
        }
    }

    private static HttpHeaders createHeaders(final String username, final String password) {
        final var httpHeaders = new HttpHeaders();
        final var auth = username + ":" + password;
        final byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(US_ASCII), false);
        final String authHeader = "Basic " + new String(encodedAuth);

        httpHeaders.set("Authorization", authHeader);

        return httpHeaders;
    }

}
