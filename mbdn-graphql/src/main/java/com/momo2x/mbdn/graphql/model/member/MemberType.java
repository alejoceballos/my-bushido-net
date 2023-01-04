package com.momo2x.mbdn.graphql.model.member;

public record MemberType(
        String id,
        String nickname,
        String name,
        String phone,
        String address,
        String email,
        String birthDate,
        String avatarId
) {
}
