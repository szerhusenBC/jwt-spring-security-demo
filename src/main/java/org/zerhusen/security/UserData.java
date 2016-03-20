package org.zerhusen.security;

/**
 * Created by stephan on 19.03.16.
 */
public class UserData {
    private Object id;
    private String username;
    private String role;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
