package com.lowPriceShop.LowPriceShop.DTO;

public class AdminDTO {
    private String email;         // Admin's email (also used for User entity)
    private String password;      // Admin's password
    private String name;          // Admin's name
    private String contact;       // Admin's contact number
    private String personalEmail; // Admin's personal email
    private String address;       // Admin's address (stored as text)
    private Integer roleId;       // Role ID (e.g., ROLE_ADMIN)

    public AdminDTO() {
    }

    public AdminDTO(String email, String password, String name, String contact, String personalEmail, String address, Integer roleId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.contact = contact;
        this.personalEmail = personalEmail;
        this.address = address;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPersonalEmail() {
        return personalEmail;
    }

    public void setPersonalEmail(String personalEmail) {
        this.personalEmail = personalEmail;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
