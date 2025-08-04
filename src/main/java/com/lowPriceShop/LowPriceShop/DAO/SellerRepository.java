package com.lowPriceShop.LowPriceShop.DAO;

import com.lowPriceShop.LowPriceShop.Entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Optional<Seller> findByEmail(String email);
    Optional<Seller> findByGstNumber(String gstNumber);
}