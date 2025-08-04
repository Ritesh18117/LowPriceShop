package com.lowPriceShop.LowPriceShop.DTO;

import com.lowPriceShop.LowPriceShop.Enum.RoleEnum;

public class UserDTO {
    private String email;    // User's email
    private String password; // User's password

    private RoleEnum role;
    public UserDTO() {
    }

    public UserDTO(String email, String password, RoleEnum roleId) {
        this.email = email;
        this.password = password;
        this.role = roleId;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum roleId) {
        this.role = role;
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
}