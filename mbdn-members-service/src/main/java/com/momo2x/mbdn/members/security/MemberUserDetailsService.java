package com.momo2x.mbdn.members.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * This is a simple injectable component that uses {@link SecurityContextHolder} to get the logged user. It was created
 * to decouple its usage from the static security context.
 */
@Component
public class MemberUserDetailsService {

    public String getUsername() {
        final var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails userDetails) {
            return userDetails.getUsername();
        }

        return principal.toString();
    }

}
