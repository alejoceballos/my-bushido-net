package com.momo2x.mbdn.members.security;

import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Needed to put Basic Authentication to work along the {@link org.springframework.security.web.SecurityFilterChain} and
 * the {@link org.springframework.security.core.userdetails.UserDetailsService} in the
 * {@link com.momo2x.mbdn.members.config.WebSecurityConfig} class.
 */
@Component
public class MemberAuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void afterPropertiesSet() {
        setRealmName("mybushidonet");
        super.afterPropertiesSet();
    }

}
