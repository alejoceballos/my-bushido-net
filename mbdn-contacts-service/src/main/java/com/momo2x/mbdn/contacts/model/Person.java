package com.momo2x.mbdn.contacts.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
public class Person extends Contact {

    @Column(length = 32, nullable = false)
    private String firstName;

    private String middleName;

    @Column(length = 35, nullable = false)
    private String lastName;

}