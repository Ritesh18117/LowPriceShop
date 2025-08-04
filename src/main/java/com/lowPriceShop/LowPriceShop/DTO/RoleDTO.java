package com.lowPriceShop.LowPriceShop.DTO;


import com.lowPriceShop.LowPriceShop.Enum.RoleEnum;

public class RoleDTO {
    private RoleEnum roleName; // Enum value like ROLE_ADMIN, ROLE_SELLER, etc.

    public RoleDTO() {
    }

    public RoleDTO(RoleEnum roleName) {
        this.roleName = roleName;
    }

    public RoleEnum getRoleName() {
        return roleName;
    }

    public void setRoleName(RoleEnum roleName) {
        this.roleName = roleName;
    }
}
