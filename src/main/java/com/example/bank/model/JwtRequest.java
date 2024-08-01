package com.example.bank.model;

import org.springframework.stereotype.Component;

/**
 * author: amitachaudhari9062@gmail.com
 * This is a Component class for jwtRequest.
 */
@Component
public class JwtRequest {
    private String name;
    private String password;

    public JwtRequest(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public JwtRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private String password;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public JwtRequest build() {
            return new JwtRequest(name, password);
        }
    }

}
