package com.lab9.models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents one login test case row from users.json.
 */
public class UserData {

    @JsonProperty("username")
    private String username;

    @JsonProperty("password")
    private String password;

    @JsonProperty("role")
    private String role;

    @JsonProperty("expectedSuccess")
    private boolean expectedSuccess;

    @JsonProperty("description")
    private String description;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public boolean isExpectedSuccess() {
        return expectedSuccess;
    }

    public String getDescription() {
        return description;
    }
}
