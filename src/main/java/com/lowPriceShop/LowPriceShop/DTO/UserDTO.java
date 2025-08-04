package com.lowPriceShop.LowPriceShop.DTO;

import com.lowPriceShop.LowPriceShop.Enum.RoleEnum;

public class UserDTO {
    private String email;    // User's email
    private String password; // User's password

    private Integer roleId;
    public UserDTO() {
    }

    public UserDTO(String email, String password, Integer roleId) {
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
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
}