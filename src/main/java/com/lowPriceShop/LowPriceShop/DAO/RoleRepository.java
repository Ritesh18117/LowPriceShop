package com.lowPriceShop.LowPriceShop.DAO;

import com.lowPriceShop.LowPriceShop.Entities.Role;
import com.lowPriceShop.LowPriceShop.Enum.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(RoleEnum roleName);

}
