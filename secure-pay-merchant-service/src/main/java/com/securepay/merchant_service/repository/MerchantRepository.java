package com.securepay.merchant_service.repository;

import com.securepay.merchant_service.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant , String> {

}
