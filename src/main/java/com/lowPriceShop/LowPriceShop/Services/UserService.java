package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.NewUserDTO;
import com.lowPriceShop.LowPriceShop.Entities.Users;

import java.util.List;

public interface UserService {

    Users addUser(NewUserDTO newUserDTO);

    Users getUserById(Integer id);

//    Users updateUser(Integer id, UserDTO userDTO);

    void deleteUser(Integer id);

    List<Users> getAllUser(Integer page, Integer size);

}
