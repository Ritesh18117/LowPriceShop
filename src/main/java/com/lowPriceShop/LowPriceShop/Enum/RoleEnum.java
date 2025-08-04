package com.lowPriceShop.LowPriceShop.Enum;

public enum RoleEnum {
    ROLE_ADMIN,
    ROLE_SELLER,
    ROLE_CUSTOMER,
    ROLE_EMPLOYEE;

    // Optional: Add a method to get the string value without "ROLE_" prefix if needed
    public String getSimpleName() {
        return this.name().replace("ROLE_", "");
    }
}