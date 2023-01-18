package com.momo2x.mbdn.graphql.schema;

public record MemberType(

        String id,
        String nickname,
        MemberContact contactSummary,
        String contactId,
        String avatarId

) {
}
