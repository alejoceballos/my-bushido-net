package com.momo2x.mbdn.members.mapping;

import com.momo2x.mbdn.members.model.MemberAvatar;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

/**
 * Most basic type of Mapstruct usage for mapping from one object to another. The only catch here is the component
 * model annotation property to allow this mapper to be injected using Spring context.
 */
@Mapper(componentModel = SPRING)
public interface AvatarMapper {

    AvatarDto toAvatarDto(MemberAvatar avatar);

}
