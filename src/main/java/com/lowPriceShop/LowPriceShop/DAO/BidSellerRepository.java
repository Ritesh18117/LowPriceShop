package com.lowPriceShop.LowPriceShop.DAO;

import com.lowPriceShop.LowPriceShop.Entities.BidSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidSellerRepository extends JpaRepository<BidSeller, Integer> {
    Page<BidSeller> findAllByIsDeletedFalse(Pageable pageable);
}
