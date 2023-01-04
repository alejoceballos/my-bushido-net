package com.momo2x.mbdn.graphql.http;

import com.momo2x.mbdn.graphql.config.EndpointProperties;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Map;

import static com.momo2x.mbdn.graphql.http.ServiceEndpointType.MEMBERS;

/**
 * From: https://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring
 */
@Component
@RequiredArgsConstructor
public class RestTemplateFactory implements FactoryBean<Map<ServiceEndpointType, RestTemplate>>, InitializingBean {

    private Map<ServiceEndpointType, RestTemplate> restTemplateMap;

    private final EndpointProperties properties;

    @Override
    public Map<ServiceEndpointType, RestTemplate> getObject() {
        return restTemplateMap;
    }

    @Override
    public Class<RestTemplate> getObjectType() {
        return RestTemplate.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws UnknownHostException {
        restTemplateMap = Map.of(
                MEMBERS, new RestTemplate(
                        new HttpComponentsClientHttpRequestFactoryBasicAuth(
                                new HttpHost(
                                        properties.getMembers().getProtocol(),
                                        InetAddress.getLocalHost(),
                                        properties.getMembers().getHost(),
                                        properties.getMembers().getPort()))));
    }

}