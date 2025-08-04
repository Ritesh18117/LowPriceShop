package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.AddressRepository;
import com.lowPriceShop.LowPriceShop.DTO.AddressDTO;
import com.lowPriceShop.LowPriceShop.Entities.Address;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AddressException.AddressNotFoundException;
import com.lowPriceShop.LowPriceShop.Services.AddressService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }


    @Override
    public Address addAddress(AddressDTO addressDTO) {
        // Validation
        if (addressDTO.getLine1() == null || addressDTO.getLine1().isEmpty()) {
            throw new IllegalArgumentException("Line 1 is required");
        }
        if (addressDTO.getCity() == null || addressDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (addressDTO.getState() == null || addressDTO.getState().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (addressDTO.getCountry() == null) {
            throw new IllegalArgumentException("Country is required");
        }
        if (addressDTO.getPincode() == null) {
            throw new IllegalArgumentException("Pincode is required");
        }

        // Create Address entity
        Address address = new Address();
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPincode(addressDTO.getPincode());
        address.setIsActive(true);
        address.setIsDeleted(false);
        address.setCreatedAt(LocalDateTime.now());

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Integer id, AddressDTO addressDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid address ID");
        }
        if (addressDTO.getLine1() == null || addressDTO.getLine1().isEmpty()) {
            throw new IllegalArgumentException("Line 1 is required");
        }
        if (addressDTO.getCity() == null || addressDTO.getCity().isEmpty()) {
            throw new IllegalArgumentException("City is required");
        }
        if (addressDTO.getState() == null || addressDTO.getState().isEmpty()) {
            throw new IllegalArgumentException("State is required");
        }
        if (addressDTO.getCountry() == null) {
            throw new IllegalArgumentException("Country is required");
        }
        if (addressDTO.getPincode() == null) {
            throw new IllegalArgumentException("Pincode is required");
        }

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + id));

        // Update Address
        address.setLine1(addressDTO.getLine1());
        address.setLine2(addressDTO.getLine2());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());
        address.setPincode(addressDTO.getPincode());
        address.setUpdatedAt(LocalDateTime.now());

        return addressRepository.save(address);
    }

    @Override
    public Address getAddressById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid address ID");
        }
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + id));

        return address;
    }

    @Override
    public void deleteAddress(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid address ID");
        }
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException("Address not found with ID: " + id));

        // Soft delete
        address.setIsDeleted(true);
        address.setDeletedAt(LocalDateTime.now());
        address.setIsActive(false);
        addressRepository.save(address);
    }

    @Override
    public List<Address> getAllAddress(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Address> addressPage = addressRepository.findAll(pageable);
        return addressPage.getContent();
    }
}
