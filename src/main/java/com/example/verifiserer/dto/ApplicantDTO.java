package com.example.verifiserer.dto;

import java.time.LocalDateTime;

public class ApplicantDTO {
    private String name;
    private String email;
    private String phone;
    private LocalDateTime createdAt;
    private String cvPath;


    public ApplicantDTO( String name, String email, String phone, LocalDateTime createdAt, String cvPath) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.createdAt = createdAt;
        this.cvPath = cvPath;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCvPath() {
        return cvPath;
    }

    public void setCvPath(String cvPath) {
        this.cvPath = cvPath;
    }
}

