package com.momo2x.mbdn.graphql.client.http;

import com.momo2x.mbdn.graphql.client.config.RemoteServiceType;
import com.momo2x.mbdn.graphql.client.config.ServiceEndpointPropertiesMap;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHost;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * From:
 * <a href="https://www.baeldung.com/how-to-use-resttemplate-with-basic-authentication-in-spring">
 * How to use rest template with basic authentication in spring
 * </a>
 */
@Component
@RequiredArgsConstructor
public class RestTemplateFactory implements FactoryBean<Map<RemoteServiceType, RestTemplate>>, InitializingBean {

    private final ServiceEndpointPropertiesMap propertiesMap;

    private Map<RemoteServiceType, RestTemplate> restTemplateMap;

    @Override
    public Map<RemoteServiceType, RestTemplate> getObject() {
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
        restTemplateMap = new HashMap<>();

        for (RemoteServiceType type : RemoteServiceType.values()) {
            restTemplateMap.put(
                    type,
                    new RestTemplate(
                            new HttpComponentsClientHttpRequestFactoryBasicAuth(
                                    new HttpHost(
                                            propertiesMap.getProperties(type).getProtocol(),
                                            InetAddress.getLocalHost(),
                                            propertiesMap.getProperties(type).getHost(),
                                            propertiesMap.getProperties(type).getPort()))));
        }
    }

}