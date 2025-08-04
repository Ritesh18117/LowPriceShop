package com.lowPriceShop.LowPriceShop.DTO;

import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Enum.RoleEnum;

public class NewUserDTO {

    private String email;

    private String password;

    private String confirmPassword;

    private RoleEnum role;

    public NewUserDTO() {
    }

    public NewUserDTO(String email, String password, String confirmPassword, RoleEnum role) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }
}
