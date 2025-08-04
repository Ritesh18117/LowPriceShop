package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.BidRepository;
import com.lowPriceShop.LowPriceShop.DAO.BidSellerRepository;
import com.lowPriceShop.LowPriceShop.DTO.BidSellerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Bid;
import com.lowPriceShop.LowPriceShop.Entities.BidSeller;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidExpiredException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidSellerException.BidSellerNotFoundException;
import com.lowPriceShop.LowPriceShop.Services.BidSellerService;
import com.lowPriceShop.LowPriceShop.Services.SellerService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BidSellerServiceImpl implements BidSellerService {

    private BidRepository  bidRepository;
    private SellerService sellerService;
    private BidSellerRepository bidSellerRepository;
    @Override
    public BidSeller addBidSeller(BidSellerDTO bidSellerDTO) {

        // 1stly validate Seller

        if(bidSellerDTO.getBidId() == null){
            throw new IllegalArgumentException("Bid Id is required!");
        }

        Bid bid = bidRepository.findById(bidSellerDTO.getBidId())
                .orElseThrow(() -> new BidNotFoundException("Bid not found with ID: " + bidSellerDTO.getBidId()));


        if(!bid.getExpirationTime().isBefore(LocalDateTime.now())){
            throw new BidExpiredException("Bid has already being expired!");
        }

        if(bidSellerDTO.getBidAmount() == null){
            throw new IllegalArgumentException("Bid amount cannot be null!");
        }

        BidSeller bidSeller = new BidSeller();
        bidSeller.setSeller(sellerService.getSellerById(bidSellerDTO.getSellerId()));
        bidSeller.setBid(bid);
        bidSeller.setBidAmount(bidSellerDTO.getBidAmount());
        bidSeller.setIsActive(true);
        bidSeller.setIsDeleted(false);
        bidSeller.setCreatedAt(LocalDateTime.now());

        return bidSellerRepository.save(bidSeller);
    }

    @Override
    public BidSeller updateBidSeller(Integer id, BidSellerDTO bidSellerDTO) {
        BidSeller existing = bidSellerRepository.findById(id)
                .orElseThrow(() -> new BidSellerNotFoundException("BidSeller not found with ID: " + id));

        // Verify Seller

        if (bidSellerDTO.getBidAmount() != null) {
            existing.setBidAmount(bidSellerDTO.getBidAmount());
        }

        if (bidSellerDTO.getSellerId() != null) {
            existing.setSeller(sellerService.getSellerById(bidSellerDTO.getSellerId()));
        }

        existing.setUpdatedAt(LocalDateTime.now());

        return bidSellerRepository.save(existing);
    }

    @Override
    public BidSeller getBidSellerById(Integer id) {
        return bidSellerRepository.findById(id)
                .orElseThrow(() -> new BidSellerNotFoundException("BidSeller not found with ID: " + id));

    }

    @Override
    public void deleteBidSeller(Integer id) {
        BidSeller existing = bidSellerRepository.findById(id)
                .orElseThrow(() -> new BidSellerNotFoundException("BidSeller not found with ID: " + id));

        existing.setIsDeleted(true);
        existing.setIsActive(false);
        existing.setDeletedAt(LocalDateTime.now());

        bidSellerRepository.save(existing);
    }

    @Override
    public List<BidSeller> getAllBidSeller(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        return bidSellerRepository.findAllByIsDeletedFalse(pageable).getContent();

    }
}
