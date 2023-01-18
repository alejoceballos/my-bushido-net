package com.momo2x.mbdn.graphql.client;

import com.momo2x.mbdn.graphql.client.config.ServiceEndpointPropertiesMap;
import com.momo2x.mbdn.graphql.client.dto.AvatarDto;
import com.momo2x.mbdn.graphql.client.dto.MemberDto;
import com.momo2x.mbdn.graphql.client.http.HttpHeadersFactory;
import com.momo2x.mbdn.graphql.client.http.RestTemplateFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import static com.momo2x.mbdn.graphql.client.config.RemoteServiceType.MEMBERS;

@Component
@RemoteServiceAdapter(MEMBERS)
public class MembersClient extends RemoteServiceClient {

    public MembersClient(
            final RestTemplateFactory restTemplateFactory,
            final HttpHeadersFactory httpHeadersFactory,
            final ServiceEndpointPropertiesMap propertiesMap) {
        super(restTemplateFactory, httpHeadersFactory, propertiesMap);
    }

    public MemberDto getById(final String id) {
        return getRestTemplate().exchange(
                        getProperties().url("get_by_id", id),
                        HttpMethod.GET,
                        new HttpEntity<String>(getHttpHeaders()),
                        MemberDto.class)
                .getBody();
    }

    public AvatarDto getAvatarByMemberId(final String id) {
        return getRestTemplate().exchange(
                        getProperties().url("get_avatar_by_member_id", id),
                        HttpMethod.GET,
                        new HttpEntity<String>(getHttpHeaders()),
                        AvatarDto.class)
                .getBody();
    }

}
