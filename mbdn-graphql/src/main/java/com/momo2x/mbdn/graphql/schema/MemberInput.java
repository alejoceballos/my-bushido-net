package com.momo2x.mbdn.graphql.schema;

public record MemberInput(

        String nickname,
        String name,
        String phone,
        String address,
        String email,
        String birthDate,
        AvatarInput avatar

) {
}
