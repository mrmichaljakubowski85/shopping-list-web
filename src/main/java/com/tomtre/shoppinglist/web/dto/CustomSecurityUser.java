package com.tomtre.shoppinglist.web.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class CustomSecurityUser extends User {

    private final long id;
    private final String fullName;

    private CustomSecurityUser(String username, String password, Collection<? extends GrantedAuthority> authorities, long id, String fullName) {
        super(username, password, authorities);
        this.id = id;
        this.fullName = fullName;
    }

    public long getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public static final class Builder {
        private long id;
        private String fullName;
        private String username;
        private String password;
        private Collection<? extends GrantedAuthority> authorities;

        public Builder() {
        }

        public Builder setId(long id) {
            this.id = id;
            return this;
        }

        public Builder setFullName(String fullName) {
            this.fullName = fullName;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public Builder setAuthorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = authorities;
            return this;
        }

        public CustomSecurityUser build() {
            return new CustomSecurityUser(username, password, authorities, id, fullName);
        }
    }
}
