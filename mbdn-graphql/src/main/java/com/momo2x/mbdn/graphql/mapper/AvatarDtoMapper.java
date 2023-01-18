package com.momo2x.mbdn.graphql.mapper;

import com.momo2x.mbdn.graphql.client.dto.AvatarDto;
import com.momo2x.mbdn.graphql.schema.AvatarType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvatarDtoMapper {

    AvatarType toAvatarType(AvatarDto avatar);

}
