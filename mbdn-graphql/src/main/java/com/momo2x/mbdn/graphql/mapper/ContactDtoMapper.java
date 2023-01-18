package com.momo2x.mbdn.graphql.mapper;

import com.momo2x.mbdn.graphql.client.dto.ContactDto;
import com.momo2x.mbdn.graphql.schema.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactDtoMapper {

    ContactType toContactType(ContactDto contact);

}
