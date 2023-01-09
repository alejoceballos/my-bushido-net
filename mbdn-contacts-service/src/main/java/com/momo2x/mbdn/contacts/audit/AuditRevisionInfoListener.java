package com.momo2x.mbdn.contacts.audit;

import com.momo2x.mbdn.contacts.security.ContactUserDetailsService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Hibernate-Envers revision listener, referenced by audit entity, to save extra data when auditing.
 * <p>
 * This class uses Spring's application context to retrieve the logged user.
 */
public class AuditRevisionInfoListener implements RevisionListener, ApplicationContextAware {

    private ContactUserDetailsService userDetails;

    public void newRevision(final Object revisionEntity) {
        final var revisionInfo = (AuditRevisionInfo) revisionEntity;
        revisionInfo.setUsername(userDetails.getUsername());
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.userDetails = applicationContext.getBean(ContactUserDetailsService.class);
    }
}
