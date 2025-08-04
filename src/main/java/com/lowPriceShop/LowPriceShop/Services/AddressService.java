package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.AddressDTO;
import com.lowPriceShop.LowPriceShop.Entities.Address;

import java.util.List;

public interface AddressService {

    Address addAddress(AddressDTO addressDTO);

    Address updateAddress(Integer id, AddressDTO addressDTO);

    Address getAddressById(Integer id);

    void deleteAddress(Integer id);

    List<Address> getAllAddress(Integer page, Integer size);
}
