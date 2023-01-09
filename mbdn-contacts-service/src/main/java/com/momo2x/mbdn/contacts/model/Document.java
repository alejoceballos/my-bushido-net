package com.momo2x.mbdn.contacts.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.util.UUID;

import static jakarta.persistence.EnumType.STRING;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
@AuditTable("document_aud")
public class Document {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    private String number;

    private String description;

    @Column(length = 56)
    private String country;

    @Enumerated(STRING)
    private DocumentType type;

}
