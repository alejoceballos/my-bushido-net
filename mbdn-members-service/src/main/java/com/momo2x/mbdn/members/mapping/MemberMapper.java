package com.momo2x.mbdn.members.mapping;

import com.momo2x.mbdn.members.constant.DateTimeConstants;
import com.momo2x.mbdn.members.model.Member;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberMapper {

    @Mapping(target = "contactRefId", source = "contact.refId")
    @Mapping(target = "name", source = "contact.name")
    @Mapping(target = "phone", source = "contact.phone")
    @Mapping(target = "address", source = "contact.address")
    @Mapping(target = "email", source = "contact.email")
    @Mapping(target = "birthDate", source = "contact.birthDate", dateFormat = DateTimeConstants.FORMAT_STRING_DATE)
    MemberDto toMemberDto(Member member);

}
