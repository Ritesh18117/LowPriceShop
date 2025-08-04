package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.SellerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Seller;

import java.util.List;

public interface SellerService {

    Seller addSeller(SellerDTO sellerDTO);

    List<Seller> getAllSeller(Integer page, Integer size);

    Seller updateSeller(Integer id, SellerDTO sellerDTO);

    void deleteSeller(Integer id);

    Seller getSellerById(Integer id);

}
