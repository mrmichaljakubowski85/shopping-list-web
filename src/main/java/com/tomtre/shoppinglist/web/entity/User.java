package com.tomtre.shoppinglist.web.entity;


import javax.persistence.*;
import java.util.List;

@Entity
@SequenceGenerator(name = BaseIdEntity.SEQUENCE_GENERATOR_NAME, sequenceName = "user_seq")
@Table(name = "users")
public class User extends TimestampIdEntity {

    public static final String USER_NAME_COLUMN_NAME = "username";
    public static final String EMAIL_COLUMN_NAME = "email";

    @Column(name = USER_NAME_COLUMN_NAME, nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = EMAIL_COLUMN_NAME, nullable = false, unique = true)
    private String email;

    //created as a list and ManyToMany relationship for future extension
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
    )
    private List<Role> roles;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
