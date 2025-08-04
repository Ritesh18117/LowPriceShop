package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.RoleRepository;
import com.lowPriceShop.LowPriceShop.DAO.SellerRepository;
import com.lowPriceShop.LowPriceShop.DAO.UserRepository;
import com.lowPriceShop.LowPriceShop.DTO.SellerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Entities.Seller;
import com.lowPriceShop.LowPriceShop.Entities.Users;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.DuplicateGstNumberException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.SellerDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.SellerException.SellerNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.SellerService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository,
                             UserRepository userRepository,
                             RoleRepository roleRepository,
                             PasswordEncoder passwordEncoder) {
        this.sellerRepository = sellerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Seller addSeller(SellerDTO sellerDTO) {
        // Validation
        if (sellerDTO.getEmail() == null || sellerDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (sellerDTO.getPassword() == null || sellerDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (sellerDTO.getName() == null || sellerDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (sellerDTO.getStoreName() == null || sellerDTO.getStoreName().isEmpty()) {
            throw new IllegalArgumentException("Store name is required");
        }
        if (sellerDTO.getGstNumber() == null || sellerDTO.getGstNumber().isEmpty()) {
            throw new IllegalArgumentException("GST number is required");
        }
        if (sellerDTO.getRoleId() == null) {
            throw new IllegalArgumentException("Role ID is required");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(sellerDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + sellerDTO.getEmail());
        }

        // Check for duplicate GST number
        if (sellerRepository.findByGstNumber(sellerDTO.getGstNumber()).isPresent()) {
            throw new DuplicateGstNumberException("GST number already in use: " + sellerDTO.getGstNumber());
        }

        // Create User entity
        Users user = new Users();
        user.setEmail(sellerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        Role role = roleRepository.findById(sellerDTO.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + sellerDTO.getRoleId()));
        user.setRole(role);
        user = userRepository.save(user);

        // Create Seller entity
        Seller seller = new Seller();
        seller.setUsers(user);
        seller.setName(sellerDTO.getName());
        seller.setStoreName(sellerDTO.getStoreName());
        seller.setStoreAddress(sellerDTO.getStoreAddress());
        seller.setStoreType(sellerDTO.getStoreType());
        seller.setEmail(sellerDTO.getEmail());
        seller.setContact(sellerDTO.getContact());
        seller.setGstNumber(sellerDTO.getGstNumber());
        seller.setIsActive(true);
        seller.setIsDeleted(false);
        seller.setCreatedAt(LocalDateTime.now());

        return sellerRepository.save(seller);

    }
    @Override
    public List<Seller> getAllSeller(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Seller> sellerPage = sellerRepository.findAll(pageable);
        return sellerPage.getContent();
    }

    @Override
    public Seller updateSeller(Integer id, SellerDTO sellerDTO) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid seller ID");
        }

        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + id));

        if (seller.getIsDeleted()) {
            throw new SellerDeletedException("Seller is deleted with ID: " + id);
        }

        // Update Seller
        seller.setName(sellerDTO.getName());
        seller.setStoreName(sellerDTO.getStoreName());
        seller.setStoreAddress(sellerDTO.getStoreAddress());
        seller.setStoreType(sellerDTO.getStoreType());
        seller.setContact(sellerDTO.getContact());
        if (sellerDTO.getGstNumber() != null) {
            seller.setGstNumber(sellerDTO.getGstNumber());
        }
        seller.setUpdatedAt(LocalDateTime.now());

        return sellerRepository.save(seller);
    }

    @Override
    public void deleteSeller(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid seller ID");
        }
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + id));
        if (seller.getIsDeleted()) {
            throw new SellerDeletedException("Seller is already deleted with ID: " + id);
        }

        // Soft delete
        seller.setIsDeleted(true);
        seller.setDeletedAt(LocalDateTime.now());
        seller.setIsActive(false);
        sellerRepository.save(seller);

        // Soft delete associated User
        Users user = seller.getUsers();
        user.setIsDeleted(true);
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public Seller getSellerById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid seller ID");
        }
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new SellerNotFoundException("Seller not found with ID: " + id));

        seller.setCreatedAt(null);
        seller.setUpdatedAt(null);
        seller.setDeletedAt(null);
        seller.getUsers().setPassword(null);

        return seller;
    }
}
