package com.momo2x.mbdn.members.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class MemberAvatar {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(length = 100)
    @NotBlank(message = "description is mandatory")
    private String description;

    @Column
    @NotNull(message = "image is mandatory")
    private byte[] image;

}
