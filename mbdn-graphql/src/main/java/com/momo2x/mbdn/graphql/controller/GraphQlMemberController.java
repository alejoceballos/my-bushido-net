package com.momo2x.mbdn.graphql.controller;

import com.momo2x.mbdn.graphql.client.ContactsClient;
import com.momo2x.mbdn.graphql.client.MembersClient;
import com.momo2x.mbdn.graphql.mapper.AvatarDtoMapper;
import com.momo2x.mbdn.graphql.mapper.ContactDtoMapper;
import com.momo2x.mbdn.graphql.mapper.MemberDtoMapper;
import com.momo2x.mbdn.graphql.schema.AvatarType;
import com.momo2x.mbdn.graphql.schema.ContactType;
import com.momo2x.mbdn.graphql.schema.MemberType;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

/**
 * A basic Spring Web MVC controller, but with methods to receive GraphQL requests. It accesses the Members' Service
 * Rest API using Spring's {@link RestTemplate}.
 */
@Controller
@RequiredArgsConstructor
public class GraphQlMemberController {

    private final MembersClient membersClient;

    private final ContactsClient contactsClient;

    private final MemberDtoMapper memberMapper;

    private final ContactDtoMapper contactMapper;

    private final AvatarDtoMapper avatarMapper;

    @QueryMapping
    public MemberType memberById(@Argument final String id) {
        return memberMapper.toMemberType(membersClient.getById(id));
    }

    @SchemaMapping(typeName = "Member", field = "avatar")
    public AvatarType avatar(final MemberType member) {
        return avatarMapper.toAvatarType(membersClient.getAvatarByMemberId(member.id()));
    }

    @SchemaMapping(typeName = "Member", field = "contactDetailed")
    public ContactType contactDetailed(final MemberType member) {
        return contactMapper.toContactType(contactsClient.getById(member.contactId()));
    }

}
