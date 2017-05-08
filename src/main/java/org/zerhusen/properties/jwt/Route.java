package org.zerhusen.properties.jwt;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * Created by glenn on 08.05.17.
 */
public class Route {
    @NestedConfigurationProperty
    private Authentication authentication;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
