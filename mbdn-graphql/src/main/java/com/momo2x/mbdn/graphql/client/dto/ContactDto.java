package com.momo2x.mbdn.graphql.client.dto;

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
        String photo

) {
}
