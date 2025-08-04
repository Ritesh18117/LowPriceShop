package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.UserDTO;
import com.lowPriceShop.LowPriceShop.Entities.Users;

import java.util.List;
import java.util.Map;

public interface UserService {

    Users addUser(UserDTO userDTO);

    Users getUserById(Integer id);

//    Users updateUser(Integer id, UserDTO userDTO);

    void deleteUser(Integer id);

    List<Users> getAllUser(Integer page, Integer size);

}
