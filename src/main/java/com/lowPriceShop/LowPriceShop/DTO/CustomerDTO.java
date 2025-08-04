package com.lowPriceShop.LowPriceShop.DTO;

import java.time.LocalDate;

public class CustomerDTO {
    private String email;        // User's email (also used for User entity)
    private String password;     // User's password
    private String name;         // Customer's name
    private String gender;       // Customer's gender
    private LocalDate dateOfBirth; // Customer's date of birth
    private String address;   // Reference to an existing Address entity
    private String contact;      // Customer's contact number
    private Integer roleId;      // Role ID (e.g., ROLE_CUSTOMER)

    public CustomerDTO() {
    }

    public CustomerDTO(String email, String password, String name, String gender, LocalDate dateOfBirth, String address, String contact, Integer roleId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.contact = contact;
        this.roleId = roleId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddressId() {
        return address;
    }

    public void setAddressId(String addressId) {
        this.address = addressId;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
