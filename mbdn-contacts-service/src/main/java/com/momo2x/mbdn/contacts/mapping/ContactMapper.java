package com.momo2x.mbdn.contacts.mapping;

import com.momo2x.mbdn.contacts.model.Contact;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static com.momo2x.mbdn.contacts.constant.DateTimeConstants.FORMAT_STRING_DATE;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface ContactMapper {

    @Mapping(target = "birthDate", source = "birthDate", dateFormat = FORMAT_STRING_DATE)
    @Mapping(target = "street", source = "address.street")
    @Mapping(target = "number", source = "address.number")
    @Mapping(target = "complement1", source = "address.complement1")
    @Mapping(target = "complement2", source = "address.complement2")
    @Mapping(target = "city", source = "address.city")
    @Mapping(target = "country", source = "address.country")
    @Mapping(target = "zipCode", source = "address.zipCode")
    ContactDto toContactDto(Contact contact);

}
