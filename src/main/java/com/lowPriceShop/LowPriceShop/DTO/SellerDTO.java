package com.lowPriceShop.LowPriceShop.DTO;

public class SellerDTO {
    private String email;        // Seller's email (also used for User entity)
    private String password;     // Seller's password
    private String name;         // Seller's name
    private String storeName;    // Name of the store
    private String storeAddress; // Address of the store
    private String storeType;   // Type of store (e.g., category ID)
    private String contact;      // Seller's contact number
    private String gstNumber;    // GST number for tax purposes
    private Integer roleId;      // Role ID (e.g., ROLE_SELLER)

    public SellerDTO() {
    }

    public SellerDTO(String email, String password, String name, String storeName, String storeAddress, String storeType, String contact, String gstNumber, Integer roleId) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.storeType = storeType;
        this.contact = contact;
        this.gstNumber = gstNumber;
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

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public String getStoreType() {
        return storeType;
    }

    public void setStoreType(String storeType) {
        this.storeType = storeType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getGstNumber() {
        return gstNumber;
    }

    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}