package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.CustomerDTO;
import com.lowPriceShop.LowPriceShop.Entities.Customer;

import java.util.List;

public interface CustomerService{

    Customer getCustomerById(Integer id);

    Customer addCustomer(CustomerDTO customerDTO);

    List<Customer> getAllCustomer(Integer page, Integer size);

    Customer updateCustomer(Integer id, CustomerDTO customerDTO);

    void deleteCustomer(Integer id);


}
