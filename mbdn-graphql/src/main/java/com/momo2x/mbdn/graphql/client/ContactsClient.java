package com.momo2x.mbdn.graphql.client;

import com.momo2x.mbdn.graphql.client.config.ServiceEndpointPropertiesMap;
import com.momo2x.mbdn.graphql.client.dto.ContactDto;
import com.momo2x.mbdn.graphql.client.http.HttpHeadersFactory;
import com.momo2x.mbdn.graphql.client.http.RestTemplateFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.momo2x.mbdn.graphql.client.config.RemoteServiceType.CONTACTS;

@Component
@RemoteServiceAdapter(CONTACTS)
public class ContactsClient extends RemoteServiceClient {

    public ContactsClient(
            final RestTemplateFactory restTemplateFactory,
            final HttpHeadersFactory httpHeadersFactory,
            final ServiceEndpointPropertiesMap propertiesMap) {
        super(restTemplateFactory, httpHeadersFactory, propertiesMap);
    }

    public ContactDto getById(final String id) {
        return getRestTemplate().exchange(
                        getProperties().url("get_by_id", id),
                        HttpMethod.GET,
                        new HttpEntity<String>(getHttpHeaders()),
                        ContactDto.class)
                .getBody();
    }

}
