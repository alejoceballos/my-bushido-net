package com.momo2x.mbdn.members.mapping;

import com.momo2x.mbdn.members.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.momo2x.mbdn.members.constant.DateTimeConstants.FORMAT_STRING_DATE;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Mapstruct mapper that "flats" entity's inner objects properties into a single object to simplify response. The
 * mapper sets the component model annotation property to allow it to be injected using Spring context.
 * <p>
 * Note: Date object is being converted to string in the yyyy-MM-dd.
 */
@Mapper(componentModel = SPRING)
public interface MemberMapper {

    @Mapping(target = "contactRefId", source = "contact.refId")
    @Mapping(target = "name", source = "contact.name")
    @Mapping(target = "phone", source = "contact.phone")
    @Mapping(target = "address", source = "contact.address")
    @Mapping(target = "email", source = "contact.email")
    @Mapping(target = "birthDate", source = "contact.birthDate", dateFormat = FORMAT_STRING_DATE)
    MemberDto toMemberDto(Member member);

}
