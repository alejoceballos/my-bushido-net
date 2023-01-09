package com.momo2x.mbdn.members.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

import static jakarta.persistence.TemporalType.DATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MemberContact {

    @Column(name = "contact_ref_id")
    private UUID refId;

    @Column(length = 255)
    private String name;

    @Column(length = 15)
    private String phone;

    @Column(length = 1000)
    private String address;

    @Column(length = 320)
    private String email;

    @Column(name = "birth_date")
    @Temporal(DATE)
    private Date birthDate;

}
