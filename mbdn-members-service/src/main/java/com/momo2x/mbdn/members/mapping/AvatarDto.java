package com.momo2x.mbdn.members.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record AvatarDto(
        String id,
        String description,
        byte[] image
) {
}
