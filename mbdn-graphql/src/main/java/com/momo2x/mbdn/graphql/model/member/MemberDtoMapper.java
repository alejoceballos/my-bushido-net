package com.momo2x.mbdn.graphql.model.member;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface MemberDtoMapper {

    MemberType toMemberType(MemberDto member);

}
