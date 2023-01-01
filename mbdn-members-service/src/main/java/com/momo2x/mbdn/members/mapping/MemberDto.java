package com.momo2x.mbdn.members.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberDto(
        String id,
        String nickname,
        String contactRefId,
        String name,
        String phone,
        String address,
        String email,
        String birthDate
) {
}
