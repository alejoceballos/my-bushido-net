package com.momo2x.mbdn.members.audit;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

/**
 * Basic Hibernate-Envers entity to audit the user responsible for the change.
 * <p>
 * This class will be automatically mapped to a table named audit_revision_info that will be used instead the default
 * revinfo table. Note that it will not use a "rev" column as the primary key anymore, it will use an "id" column
 * instead.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@RevisionEntity(AuditRevisionInfoListener.class)
public class AuditRevisionInfo extends DefaultRevisionEntity {

    private String username;

}
