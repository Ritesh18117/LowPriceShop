package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.BidSellerDTO;
import com.lowPriceShop.LowPriceShop.Entities.BidSeller;

import java.util.List;

// Seller who have bid into the requirement Bid by customer
public interface BidSellerService {

    BidSeller addBidSeller(BidSellerDTO bidSellerDTO);

    BidSeller updateBidSeller(Integer id, BidSellerDTO bidSellerDTO);

    BidSeller getBidSellerById(Integer id);

    void deleteBidSeller(Integer id);

    List<BidSeller> getAllBidSeller(Integer page, Integer size);
}
