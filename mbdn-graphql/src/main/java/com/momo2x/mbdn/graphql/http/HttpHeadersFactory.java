package com.momo2x.mbdn.graphql.http;

import com.momo2x.mbdn.graphql.config.EndpointProperties;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Map;

import static com.momo2x.mbdn.graphql.http.ServiceEndpointType.MEMBERS;
import static java.nio.charset.StandardCharsets.US_ASCII;

@Component
@RequiredArgsConstructor
public class HttpHeadersFactory implements FactoryBean<Map<ServiceEndpointType, HttpHeaders>>, InitializingBean {

    private final EndpointProperties properties;

    private Map<ServiceEndpointType, HttpHeaders> httpHeadersMap;

    @Override
    public Map<ServiceEndpointType, HttpHeaders> getObject() throws Exception {
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
        httpHeadersMap = Map.of(
                MEMBERS,
                createHeaders(
                        properties.getMembers().getUsername(),
                        properties.getMembers().getPassword()));
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
