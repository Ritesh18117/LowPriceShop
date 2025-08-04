package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.RoleRepository;
import com.lowPriceShop.LowPriceShop.DAO.UserRepository;
import com.lowPriceShop.LowPriceShop.DTO.UserDTO;
import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Entities.Users;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.UserNotFoundException;
import com.lowPriceShop.LowPriceShop.Services.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public Users addUser(UserDTO userDTO) {
        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (userDTO.getRoleId() == null) {
            throw new IllegalArgumentException("Role ID is required");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + userDTO.getEmail());
        }

        // Create User entity
        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        // Fetch Role by ID
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id: " + userDTO.getRoleId()));
        user.setRole(role);

        // Save and return User
        return userRepository.save(user);
    }

    @Override
    public Users getUserById(Integer id) {
        // Validation
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Optional<Users> user = userRepository.findById(id);

        if(user.isEmpty()){
            throw new UserNotFoundException("User not found with id : " + id);
        }

        user.get().setCreatedAt(null);
        user.get().setIsDeleted(null);
        user.get().setIsActive(null);
        user.get().setPassword(null);
        user.get().setUpdatedAt(null);
        user.get().setDeletedAt(null);

        return user.get();
    }

//    @Override
//    public Users updateUser(Integer id, UserDTO userDTO) {
//
//        // Validation
//        if (id == null || id <= 0) {
//            throw new IllegalArgumentException("Invalid user ID");
//        }
//        if (userDTO.getEmail() == null || userDTO.getEmail().isEmpty()) {
//            throw new IllegalArgumentException("Email is required");
//        }
//
//        // Fetch existing User
//        Users user = userRepository.findById(id)
//                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
//
//        // Check for email conflict (if email is changing)
//        if (!user.getPassword().equals(userDTO.getEmail()) &&
//                userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new DuplicateEmailException("Email already in use: " + userDTO.getEmail());
//        }
//
//        // Update fields
//        user.setEmail(userDTO.getEmail());
//        if (userDTO.getPassword() != null && !userDTO.getPassword().isEmpty()) {
//            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        }
//
//        return null;
//    }

    @Override
    public void deleteUser(Integer id) {
        // Validation
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        // Check if User exists
        Users user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        // Delete User
        userRepository.delete(user);
    }

    @Override
    public List<Users> getAllUser(Integer page, Integer size) {
        // Validation
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        // Create Pageable object
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated Users
        Page<Users> userPage = userRepository.findAll(pageable);
        return userPage.getContent();
    }
}
