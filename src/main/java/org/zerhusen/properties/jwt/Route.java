package org.zerhusen.properties.jwt;

import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.io.Serializable;

/**
 * Created by glenn on 08.05.17.
 */
public class Route implements Serializable {
    @NestedConfigurationProperty
    private Authentication authentication;

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
