package com.lowPriceShop.LowPriceShop.DTO;

import java.math.BigDecimal;

public class BidSellerDTO {
    private Integer sellerId;   // ID of the seller making the bid
    private Integer bidId;      // ID of the bid being responded to
    private BigDecimal bidAmount; // Amount offered by the seller

    public BidSellerDTO() {
    }

    public BidSellerDTO(Integer sellerId, Integer bidId, BigDecimal bidAmount) {
        this.sellerId = sellerId;
        this.bidId = bidId;
        this.bidAmount = bidAmount;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public void setBidId(Integer bidId) {
        this.bidId = bidId;
    }

    public BigDecimal getBidAmount() {
        return bidAmount;
    }

    public void setBidAmount(BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }
}