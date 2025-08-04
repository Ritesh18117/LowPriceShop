package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.BidRepository;
import com.lowPriceShop.LowPriceShop.DAO.CustomerRepository;
import com.lowPriceShop.LowPriceShop.DTO.BidDTO;
import com.lowPriceShop.LowPriceShop.Entities.Bid;
import com.lowPriceShop.LowPriceShop.Entities.Customer;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidInactiveException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.BidException.BidNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerInactiveException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.UnauthorizedCustomerException;
import com.lowPriceShop.LowPriceShop.Services.BidService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BidServiceImpl implements BidService {

    private final BidRepository bidRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public BidServiceImpl(BidRepository bidRepository, CustomerRepository customerRepository) {
        this.bidRepository = bidRepository;
        this.customerRepository = customerRepository;
    }


    @Override
    @Transactional
    public Bid addBid(BidDTO bidDTO) {
        // Validation
        if (bidDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }
        if (bidDTO.getProductName() == null || bidDTO.getProductName().isEmpty()) {
            throw new IllegalArgumentException("Product name is required");
        }
        if (bidDTO.getQuantity() == null || bidDTO.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        if (bidDTO.getUnit() == null || bidDTO.getUnit().isEmpty()) {
            throw new IllegalArgumentException("Unit is required");
        }
        if (bidDTO.getExpirationTime() == null) {
            throw new IllegalArgumentException("Expiration time is required");
        }
        if (bidDTO.getExpirationTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Expiration time must be in the future");
        }

        // Validate Customer
        Customer customer = customerRepository.findById(bidDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + bidDTO.getCustomerId()));
        if (!customer.getIsActive()) {
            throw new CustomerInactiveException("Customer is inactive with ID: " + bidDTO.getCustomerId());
        }
        if (customer.getIsDeleted()) {
            throw new CustomerDeletedException("Customer is deleted with ID: " + bidDTO.getCustomerId());
        }

        // Create Bid entity
        Bid bid = new Bid();
        bid.setCustomer(customer);
        bid.setProductName(bidDTO.getProductName());
        bid.setQuantity(bidDTO.getQuantity());
        bid.setUnit(bidDTO.getUnit());
        bid.setExpirationTime(bidDTO.getExpirationTime());
        bid.setIsActive(true);
        bid.setIsDeleted(false);
        bid.setCreatedAt(LocalDateTime.now());

        return bidRepository.save(bid);
    }

    @Override
    public Bid getBidById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid bid ID");
        }
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found with ID: " + id));

        bid.getCustomer().getUsers().setPassword(null);
        return bid;
    }

    @Override
    public Bid updateBid(Integer id, BidDTO bidDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid bid ID");
        }
        if (bidDTO.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID is required");
        }

        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found with ID: " + id));

        if(!Objects.equals(bidDTO.getCustomerId(),bid.getCustomer().getId())){
            throw new UnauthorizedCustomerException("Customer not authorized");
        }

        if (!bid.getIsActive()) {
            throw new BidInactiveException("Bid is inactive with ID: " + id);
        }
        if (bid.getIsDeleted()) {
            throw new BidDeletedException("Bid is deleted with ID: " + id);
        }

        // Validate Customer
        Customer customer = customerRepository.findById(bidDTO.getCustomerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + bidDTO.getCustomerId()));
        if (!customer.getIsActive()) {
            throw new CustomerInactiveException("Customer is inactive with ID: " + bidDTO.getCustomerId());
        }
        if (customer.getIsDeleted()) {
            throw new CustomerDeletedException("Customer is deleted with ID: " + bidDTO.getCustomerId());
        }

        // Update Bid
        bid.setProductName(bidDTO.getProductName());
        bid.setQuantity(bidDTO.getQuantity());
        bid.setUnit(bidDTO.getUnit());
        bid.setExpirationTime(bidDTO.getExpirationTime());
        bid.setUpdatedAt(LocalDateTime.now());

        return bidRepository.save(bid);

    }

    @Override
    public void deleteBid(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid bid ID");
        }
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found with ID: " + id));
        if (bid.getIsDeleted()) {
            throw new BidDeletedException("Bid is already deleted with ID: " + id);
        }

        // Soft delete
        bid.setIsDeleted(true);
        bid.setDeletedAt(LocalDateTime.now());
        bid.setIsActive(false);
        bidRepository.save(bid);
    }

    @Override
    public List<Bid> getAllBid(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Bid> bidPage = bidRepository.findAll(pageable);
        return bidPage.getContent();
    }

    @Override
    public List<Bid> getBidByCustomerId(Integer customerId) {
        if (customerId == null || customerId <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        // Validate Customer
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + customerId));

        if (customer.getIsDeleted()) {
            throw new CustomerDeletedException("Customer is deleted with ID: " + customerId);
        }

        return bidRepository.findByCustomerIdAndIsDeletedFalse(customerId);
    }
}
