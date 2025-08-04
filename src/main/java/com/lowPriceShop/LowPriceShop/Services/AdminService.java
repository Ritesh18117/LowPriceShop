package com.lowPriceShop.LowPriceShop.Services;

import com.lowPriceShop.LowPriceShop.DTO.AdminDTO;
import com.lowPriceShop.LowPriceShop.Entities.Admin;

import java.util.List;

public interface AdminService {

    Admin addAdmin(AdminDTO adminDTO);

    Admin updateAdmin(Integer id, AdminDTO adminDTO);

    Admin getAdminById(Integer id);

    void deleteAdmin(Integer id);

    List<Admin> getAllAdmin(Integer page, Integer size);

}
