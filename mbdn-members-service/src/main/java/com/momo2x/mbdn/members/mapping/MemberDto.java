package com.momo2x.mbdn.members.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Simple DTO using Java records to decouple module's inner structures from the outside world. Note that the avatar id
 * nor its image data are being stored here. There is a second request that must made to retrieve the image, mapped by
 * another DTO (that's why it is being lazy loaded in the entity model).
 * <p>
 * Null data will be excluded in the final JSON response body.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MemberDto(
        String id,
        String nickname,
        String contactRefId,
        String name,
        String phone,
        String address,
        String email,
        String birthDate,
        String contactRef
) {
}
