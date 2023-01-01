package com.momo2x.mbdn.members.mapping;

import com.momo2x.mbdn.members.model.MemberAvatar;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AvatarMapper {

    AvatarDto toAvatarDto(MemberAvatar avatar);

}
