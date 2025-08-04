package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.RoleDTO;
import com.lowPriceShop.LowPriceShop.Entities.Role;

import java.util.List;

public interface RoleService {

    Role addRole(RoleDTO roleDTO);

    List<Role> getAllRole(Integer page,Integer size);

    void deleteRole(Integer id);

}
