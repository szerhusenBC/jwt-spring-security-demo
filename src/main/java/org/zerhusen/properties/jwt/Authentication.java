package org.zerhusen.properties.jwt;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;

/**
 * Created by glenn on 08.05.17.
 */
public class Authentication implements Serializable {
    @NotEmpty
    private String path;
    @NotEmpty
    private String refresh;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }
}
