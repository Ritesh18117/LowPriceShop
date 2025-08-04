package com.lowPriceShop.LowPriceShop.DAO;

import com.lowPriceShop.LowPriceShop.Entities.Customer;
import com.lowPriceShop.LowPriceShop.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByEmail(String email);

}