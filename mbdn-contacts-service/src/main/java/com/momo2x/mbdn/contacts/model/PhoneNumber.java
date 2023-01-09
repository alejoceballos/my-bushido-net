package com.momo2x.mbdn.contacts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@AuditTable("phone_number_aud")
public class PhoneNumber {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(length = 3)
    private String countryCode;

    @Column(length = 3)
    private String areaCode;

    @Column(length = 3, nullable = false)
    private String number;

    private boolean main;

}
