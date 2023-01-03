package com.momo2x.mbdn.members.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.envers.Audited;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Audited
public class Member {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(length = 20, nullable = false)
    @NotBlank(message = "nickname is mandatory")
    private String nickname;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private MemberAvatar avatar;

    @Embedded
    private MemberContact contact;

}

