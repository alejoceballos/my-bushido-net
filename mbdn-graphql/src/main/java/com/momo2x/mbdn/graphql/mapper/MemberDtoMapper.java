package com.momo2x.mbdn.graphql.mapper;

import com.momo2x.mbdn.graphql.client.dto.MemberDto;
import com.momo2x.mbdn.graphql.schema.MemberType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberDtoMapper {

    @Mapping(source = "name", target = "contactSummary.name")
    @Mapping(source = "phone", target = "contactSummary.phone")
    @Mapping(source = "address", target = "contactSummary.address")
    @Mapping(source = "email", target = "contactSummary.email")
    @Mapping(source = "birthDate", target = "contactSummary.birthDate")
    @Mapping(source = "contactRef", target = "contactId")
    MemberType toMemberType(MemberDto member);

}
