package com.momo2x.mbdn.graphql.model.member;

public record MemberDto(
        String id,
        String nickname,
        String name,
        String phone,
        String address,
        String email,
        String birthDate
) {
}
