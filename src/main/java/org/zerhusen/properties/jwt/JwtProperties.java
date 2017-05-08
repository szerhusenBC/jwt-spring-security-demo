package org.zerhusen.properties.jwt;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.io.Serializable;

/**
 * Created by glenn on 08.05.17.
 */

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties implements Serializable {
    @NotEmpty
    private String header;
    @NotEmpty
    private String secret;
    @Valid
    private Long expiration;

    @NestedConfigurationProperty
    private Route route;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }
}
