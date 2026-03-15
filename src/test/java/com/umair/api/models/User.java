package com.umair.api.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * POJO representing a User resource from reqres.in API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private int    id;
    private String email;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private String avatar;

    public User() {}

    public User(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName  = lastName;
        this.email     = email;
    }

    // Getters
    public int    getId()        { return id; }
    public String getEmail()     { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName()  { return lastName; }
    public String getAvatar()    { return avatar; }

    // Setters
    public void setId(int id)               { this.id = id; }
    public void setEmail(String email)      { this.email = email; }
    public void setFirstName(String n)      { this.firstName = n; }
    public void setLastName(String n)       { this.lastName = n; }
    public void setAvatar(String avatar)    { this.avatar = avatar; }

    @Override
    public String toString() {
        return String.format("User{id=%d, name='%s %s', email='%s'}", id, firstName, lastName, email);
    }
}
