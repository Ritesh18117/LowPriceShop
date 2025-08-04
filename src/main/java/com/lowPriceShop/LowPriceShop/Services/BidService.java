package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.BidDTO;
import com.lowPriceShop.LowPriceShop.Entities.Bid;

import java.util.List;

public interface BidService {

    Bid addBid(BidDTO bidDTO);

    Bid getBidById(Integer id);

    Bid updateBid(Integer id, BidDTO bidDTO);

    void deleteBid(Integer id);

    List<Bid> getAllBid(Integer page, Integer size);

    List<Bid> getBidByCustomerId(Integer customerId);

}
