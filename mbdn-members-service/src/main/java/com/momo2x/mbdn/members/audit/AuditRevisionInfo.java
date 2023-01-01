package com.momo2x.mbdn.members.audit;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(AuditRevisionInfoListener.class)
public class AuditRevisionInfo extends DefaultRevisionEntity {

    private String username;

}
