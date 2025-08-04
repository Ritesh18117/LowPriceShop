package com.lowPriceShop.LowPriceShop.Services.ServiceImpl;

import com.lowPriceShop.LowPriceShop.DAO.RoleRepository;
import com.lowPriceShop.LowPriceShop.DTO.RoleDTO;
import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.DuplicateRoleNameException;
import com.lowPriceShop.LowPriceShop.ErrorHandling.Exceptions.RoleException.RoleNotFoundException;
import com.lowPriceShop.LowPriceShop.Services.RoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role addRole(RoleDTO roleDTO) {

        if(roleDTO.getRoleName() == null){
            throw new IllegalArgumentException("Role name is required!");
        }

        if(roleRepository.findByRoleName(roleDTO.getRoleName()).isPresent()){
            throw new DuplicateRoleNameException("Role name already exists : " + roleDTO.getRoleName());
        }

        Role role = new Role();
        role.setRoleName(roleDTO.getRoleName());
        role.setActive(true);
        role.setDeleted(false);
        role.setCreatedAt(LocalDateTime.now());

        return roleRepository.save(role);
    }

    @Override
    public List<Role> getAllRole(Integer page, Integer size) {

        Pageable pageable = PageRequest.of(page,size);

        Page<Role> rolePage = roleRepository.findAll(pageable);

        if(rolePage.getSize() == 0){
            throw new RoleNotFoundException("Role not found!");
        }

        return rolePage.getContent();
    }

    @Override
    public void deleteRole(Integer id) {

        if(id == null || id <= 0){
            throw new IllegalArgumentException("Invalid role ID");
        }

        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with id : " + id));

        roleRepository.delete(role);
    }
}
