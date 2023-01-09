package com.momo2x.mbdn.contacts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.InheritanceType.JOINED;
import static jakarta.persistence.TemporalType.DATE;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = JOINED)
@Audited
public abstract class Contact {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(length = 320)
    private String email;

    @Temporal(DATE)
    private Date birthDate;

    private byte[] photo;

    @Column(length = 1000)
    private String referenceContact;

    @Embedded
    private Address address;

    @OneToMany(cascade = ALL, fetch = LAZY, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private List<PhoneNumber> phoneNumbers;

    @OneToMany(cascade = ALL, fetch = LAZY, orphanRemoval = true)
    @JoinColumn(name = "contact_id")
    private List<Document> documents;

}
