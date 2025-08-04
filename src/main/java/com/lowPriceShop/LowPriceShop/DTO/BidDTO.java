package com.lowPriceShop.LowPriceShop.DTO;

import java.time.LocalDateTime;

public class BidDTO {
    private Integer customerId;    // ID of the customer placing the bid
    private String productName;    // Name of the product being bid on
    private Integer quantity;      // Quantity requested
    private String unit;           // Unit of measurement (e.g., "kg", "units")
    private LocalDateTime expirationTime; // When the bid expires

    public BidDTO() {
    }

    public BidDTO(Integer customerId, String productName, Integer quantity, String unit, LocalDateTime expirationTime) {
        this.customerId = customerId;
        this.productName = productName;
        this.quantity = quantity;
        this.unit = unit;
        this.expirationTime = expirationTime;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public LocalDateTime getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(LocalDateTime expirationTime) {
        this.expirationTime = expirationTime;
    }
}
