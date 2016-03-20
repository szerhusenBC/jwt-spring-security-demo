package org.zerhusen.security;

import org.springframework.security.core.authority.AuthorityUtils;
import org.zerhusen.model.security.JwtUserEntity;

/**
 * Created by stephan on 20.03.16.
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }

    public static JwtUser create(JwtUserEntity user) {
        return new JwtUser(
                user.getId(),
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getLastPasswordReset(),
                AuthorityUtils.commaSeparatedStringToAuthorityList(user.getAuthorities())
        );
    }
}
