package com.lowPriceShop.LowPriceShop.DAO;

import com.lowPriceShop.LowPriceShop.Entities.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Integer> {
    List<Bid> findByCustomerIdAndIsDeletedFalse(Integer customerId);
}