package com.momo2x.mbdn.contacts.mapping;

import com.fasterxml.jackson.annotation.JsonInclude;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@JsonInclude(NON_NULL)
public record ContactDto(

        String id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String birthDate,
        String referenceContact,
        String street,
        String number,
        String complement1,
        String complement2,
        String city,
        String country,
        String zipCode,
        byte[] photo

) {
}
