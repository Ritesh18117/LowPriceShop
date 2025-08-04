package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.AdminRepository;
import com.lowPriceShop.LowPriceShop.DAO.RoleRepository;
import com.lowPriceShop.LowPriceShop.DAO.UserRepository;
import com.lowPriceShop.LowPriceShop.DTO.AdminDTO;
import com.lowPriceShop.LowPriceShop.Entities.Admin;
import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Entities.Users;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AdminException.AdminDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AdminException.AdminInactiveException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.AdminException.AdminNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.AdminService;
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
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository,
                            UserRepository userRepository,
                            RoleRepository roleRepository,
                            PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Admin addAdmin(AdminDTO adminDTO) {
        // Validation
        if (adminDTO.getEmail() == null || adminDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (adminDTO.getPassword() == null || adminDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (adminDTO.getName() == null || adminDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (adminDTO.getRoleId() == null) {
            throw new IllegalArgumentException("Role ID is required");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(adminDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + adminDTO.getEmail());
        }

        // Check for duplicate personal email
        if (adminDTO.getPersonalEmail() != null && !adminDTO.getPersonalEmail().isEmpty() &&
                adminRepository.findByEmail(adminDTO.getPersonalEmail()).isPresent()) {
            throw new DuplicateEmailException("Personal email already in use: " + adminDTO.getPersonalEmail());
        }

        // Create User entity
        Users user = new Users();
        user.setEmail(adminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminDTO.getPassword()));
        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        Role role = roleRepository.findById(adminDTO.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + adminDTO.getRoleId()));
        user.setRole(role);

        user = userRepository.save(user);

        // Create Admin entity
        Admin admin = new Admin();
        admin.setUsers(user);
        admin.setName(adminDTO.getName());
        admin.setEmail(adminDTO.getEmail());
        admin.setContact(adminDTO.getContact());
        admin.setPersonalEmail(adminDTO.getPersonalEmail());
        admin.setAddress(adminDTO.getAddress());
        admin.setIsActive(true);
        admin.setIsDeleted(false);
        admin.setCreatedAt(LocalDateTime.now());

        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(Integer id, AdminDTO adminDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid admin ID");
        }
        if (adminDTO.getName() == null || adminDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }

        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + id));
//        if (!admin.getIsActive()) {
//            throw new AdminInactiveException("Admin is inactive with ID: " + id);
//        }
        if (admin.getIsDeleted()) {
            throw new AdminDeletedException("Admin is deleted with ID: " + id);
        }

        // Update Admin
        admin.setName(adminDTO.getName());
        admin.setContact(adminDTO.getContact());
        admin.setPersonalEmail(adminDTO.getPersonalEmail());
        admin.setAddress(adminDTO.getAddress());
        admin.setUpdatedAt(LocalDateTime.now());

        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminById(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid admin ID");
        }
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + id));

        admin.getUsers().setPassword(null);
        admin.setCreatedAt(null);
        admin.setUpdatedAt(null);
        admin.setDeletedAt(null);

        return admin;
    }

    @Override
    public void deleteAdmin(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid admin ID");
        }
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Admin not found with ID: " + id));
        if (admin.getIsDeleted()) {
            throw new AdminDeletedException("Admin is already deleted with ID: " + id);
        }

        // Soft delete
        admin.setIsDeleted(true);
        admin.setDeletedAt(LocalDateTime.now());
        admin.setIsActive(false);
        adminRepository.save(admin);

        // Soft delete associated User
        Users user = admin.getUsers();
        user.setIsDeleted(true);
        user.setIsActive(false);
        userRepository.save(user);
    }

    @Override
    public List<Admin> getAllAdmin(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Admin> adminPage = adminRepository.findAll(pageable);

        return adminPage.getContent();
    }
}
