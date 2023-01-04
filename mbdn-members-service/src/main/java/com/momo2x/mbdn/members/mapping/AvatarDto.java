package com.momo2x.mbdn.members.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

/**
 * Simple DTO using Java records to decouple module's inner structures from the outside world.
 * <p>
 * Null data will be excluded in the final JSON response body.
 * <p>
 * The image will be transformed in a Base64 ASCII representation.
 */
@JsonInclude(NON_NULL)
public record AvatarDto(
        String id,
        String description,
        byte[] image
) {
}
