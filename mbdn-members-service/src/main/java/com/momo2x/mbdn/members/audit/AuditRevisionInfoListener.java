package com.momo2x.mbdn.members.audit;

import com.momo2x.mbdn.members.security.MemberUserDetailsService;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AuditRevisionInfoListener implements RevisionListener, ApplicationContextAware {

    private MemberUserDetailsService userDetails;

    public void newRevision(final Object revisionEntity) {
        final var revisionInfo = (AuditRevisionInfo) revisionEntity;
        revisionInfo.setUsername(userDetails.getUsername());
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.userDetails = applicationContext.getBean(MemberUserDetailsService.class);
    }
}
