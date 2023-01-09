package com.momo2x.mbdn.contacts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Address {

    @Column(length = 85)
    private String street;

    @Column(name = "street_number", length = 10)
    private String number;

    @Column(name = "address_complement1")
    private String complement1;

    @Column(name = "address_complement2")
    private String complement2;

    @Column(length = 176)
    private String city;

    @Column(length = 56)
    private String country;

    @Column(length = 11)
    private String zipCode;

}
