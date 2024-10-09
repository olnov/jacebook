package com.makersacademy.acebook.model;
import lombok.Data;

public class UserDTO {
    public Long id;
    public String username;

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    // Getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
