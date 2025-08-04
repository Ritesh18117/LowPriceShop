package com.lowPriceShop.LowPriceShop.DTO;

public class AddressDTO {
    private String line1;    // First line of the address
    private String line2;    // Second line of the address (optional)
    private String city;     // City name
    private String state;    // State name
    private Integer country; // Country code or ID
    private Integer pincode; // Postal code

    public AddressDTO() {
    }

    public AddressDTO(String line1, String line2, String city, String state, Integer country, Integer pincode) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getCountry() {
        return country;
    }

    public void setCountry(Integer country) {
        this.country = country;
    }

    public Integer getPincode() {
        return pincode;
    }

    public void setPincode(Integer pincode) {
        this.pincode = pincode;
    }
}