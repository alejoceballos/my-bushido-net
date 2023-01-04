package com.momo2x.mbdn.graphql.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Wrapper for all properties that enable communicating to the Members Service.
 * <p>
 * Each time a new service that is implemented, a new component wrapping its settings must be created extending the
 * {@link ServiceEndpointProperties}.
 */
@Component
@NoArgsConstructor
public class MembersEndpointProperties extends ServiceEndpointProperties {

    @Getter
    @Value("${com.momo2x.mbdn.members.protocol}")
    private String protocol;

    @Getter
    @Value("${com.momo2x.mbdn.members.host}")
    private String host;

    @Getter
    @Value("${com.momo2x.mbdn.members.port}")
    private int port;

    @Getter
    @Value("${com.momo2x.mbdn.members.username}")
    private String username;

    @Getter
    @Value("${com.momo2x.mbdn.members.password}")
    private String password;

    @Value("${com.momo2x.mbdn.members.api.get_by_id}")
    private String getByIdEntryPoint;

    @Value("${com.momo2x.mbdn.members.api.get_avatar_by_member_id}")
    private String getAvatarByMemberIdEntryPoint;

    public String getGetByIdEntryPoint(final String id) {
        return getByIdEntryPoint.formatted(id);
    }

    public String getGetAvatarByMemberIdEntryPoint(final String id) {
        return getAvatarByMemberIdEntryPoint.formatted(id);
    }
}
