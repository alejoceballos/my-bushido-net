package com.momo2x.mbdn.graphql.controller;

import com.momo2x.mbdn.graphql.config.EndpointProperties;
import com.momo2x.mbdn.graphql.config.MembersEndpointProperties;
import com.momo2x.mbdn.graphql.exception.MemberEndpointException;
import com.momo2x.mbdn.graphql.http.HttpHeadersFactory;
import com.momo2x.mbdn.graphql.http.RestTemplateFactory;
import com.momo2x.mbdn.graphql.model.member.*;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import static com.momo2x.mbdn.graphql.http.ServiceEndpointType.MEMBERS;

/**
 * A basic Spring Web MVC controller, but with methods to receive GraphQL requests. It accesses the Members' Service
 * Rest API using Spring's {@link RestTemplate}.
 */
@Controller
@RequiredArgsConstructor
public class GraphQlMemberController {

    private final RestTemplateFactory restTemplateFactory;

    private final HttpHeadersFactory httpHeadersFactory;

    private final EndpointProperties properties;

    private final MemberDtoMapper memberMapper;

    private final AvatarDtoMapper avatarMapper;

    @QueryMapping
    public MemberType memberById(@Argument final String id) {
        var responseEntity = getRestTemplate().exchange(
                getProperties().url(getProperties().getGetByIdEntryPoint(id)),
                HttpMethod.GET,
                new HttpEntity<String>(getHttpHeaders()),
                MemberDto.class);

        return memberMapper.toMemberType(responseEntity.getBody());
    }

    @SchemaMapping(typeName = "Member", field = "avatar")
    public AvatarType avatar(final MemberType member) {

        var responseEntity = getRestTemplate().exchange(
                getProperties().url(getProperties().getGetAvatarByMemberIdEntryPoint(member.id())),
                HttpMethod.GET,
                new HttpEntity<String>(getHttpHeaders()),
                AvatarDto.class);

        return avatarMapper.toAvatarType(responseEntity.getBody());
    }

    private RestTemplate getRestTemplate() {
        return restTemplateFactory.getObject().get(MEMBERS);
    }

    private HttpHeaders getHttpHeaders() {
        try {
            return httpHeadersFactory.getObject().get(MEMBERS);
        } catch (Exception e) {
            throw new MemberEndpointException(e);
        }
    }

    private MembersEndpointProperties getProperties() {
        return properties.getMembers();
    }
}
