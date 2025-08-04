package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.AddressRepository;
import com.lowPriceShop.LowPriceShop.DAO.CustomerRepository;
import com.lowPriceShop.LowPriceShop.DAO.RoleRepository;
import com.lowPriceShop.LowPriceShop.DAO.UserRepository;
import com.lowPriceShop.LowPriceShop.DTO.CustomerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Customer;
import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Entities.Users;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerDeletedException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerInactiveException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.CustomerException.CustomerNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.UserException.DuplicateEmailException;
import com.lowPriceShop.LowPriceShop.Services.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository,
                               UserRepository userRepository,
                               RoleRepository roleRepository,
                               AddressRepository addressRepository,
                               PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.addressRepository = addressRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Customer getCustomerById(Integer id) {

        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));


        // Will throw the below exceptions in case of Not ADMIN;

//        if (!customer.getIsActive()) {
//            throw new CustomerInactiveException("Customer is inactive with ID: " + id);
//        }

//        if (customer.getIsDeleted()) {
//            throw new CustomerDeletedException("Customer is deleted with ID: " + id);
//        }

        customer.setCreatedAt(null);
        customer.setUpdatedAt(null);
        customer.setDeletedAt(null);
        customer.getUsers().setPassword(null);

        return customer;
    }

    @Override
    public Customer addCustomer(CustomerDTO customerDTO) {
        // Validation
        if (customerDTO.getEmail() == null || customerDTO.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (customerDTO.getPassword() == null || customerDTO.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (customerDTO.getName() == null || customerDTO.getName().isEmpty()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (customerDTO.getRoleId() == null) {
            throw new IllegalArgumentException("Role ID is required");
        }

        // Check for duplicate email
        if (userRepository.findByEmail(customerDTO.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already in use: " + customerDTO.getEmail());
        }

        // Create User entity
        Users user = new Users();
        user.setEmail(customerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(customerDTO.getPassword()));
        user.setIsActive(true);
        user.setIsDeleted(false);
        user.setCreatedAt(LocalDateTime.now());

        Role role = roleRepository.findById(customerDTO.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + customerDTO.getRoleId()));
        user.setRole(role);

        userRepository.save(user);

        // Create Customer entity
        Customer customer = new Customer();
        customer.setUsers(user);
        customer.setName(customerDTO.getName());
        customer.setGender(customerDTO.getGender());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());

        customer.setEmail(customerDTO.getEmail());
        customer.setContact(customerDTO.getContact());
        customer.setRoleId(customerDTO.getRoleId());
        customer.setIsActive(true);
        customer.setIsDeleted(false);
        customer.setCreatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomer(Integer page, Integer size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAll(pageable);

        return customerPage.getContent();
    }

    @Override
    public Customer updateCustomer(Integer id, CustomerDTO customerDTO) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));

        if (!customer.getIsActive()) {
            throw new CustomerInactiveException("Customer is inactive with ID: " + id);
        }
        if (customer.getIsDeleted()) {
            throw new CustomerDeletedException("Customer is deleted with ID: " + id);
        }

        // Update Customer
        customer.setName(customerDTO.getName());
        customer.setGender(customerDTO.getGender());
        customer.setDateOfBirth(customerDTO.getDateOfBirth());
        customer.setContact(customerDTO.getContact());
        customer.setUpdatedAt(LocalDateTime.now());

        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Integer id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found with ID: " + id));
        if (customer.getIsDeleted()) {
            throw new CustomerDeletedException("Customer is already deleted with ID: " + id);
        }

        // Soft delete
        customer.setIsDeleted(true);
        customer.setDeletedAt(LocalDateTime.now());
        customer.setIsActive(false);
        customerRepository.save(customer);

        // Optionally soft delete associated User
        Users user = customer.getUsers();
        user.setIsDeleted(true);
        user.setIsActive(false);
        userRepository.save(user);

    }
}
