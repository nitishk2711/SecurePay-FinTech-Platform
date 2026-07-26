package com.securepay.merchant_service.service.serviceImpl;

import com.securepay.merchant_service.component.ApiKeyGenerator;
import com.securepay.merchant_service.entity.ApiKey;
import com.securepay.merchant_service.entity.Merchant;
import com.securepay.merchant_service.repository.ApiKeyRepository;
import com.securepay.merchant_service.repository.MerchantRepository;
import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.response.MerchantResponse;
import com.securepay.merchant_service.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyGenerator generator;

    @Override
    public MerchantResponse onboard(MerchantRequest request) {

        Merchant merchant = new Merchant();
        merchant.setMerchantId("MID" + UUID.randomUUID().toString().substring(0, 8));
        merchant.setBusinessName(request.getBusinessName());
        merchant.setEmail(request.getEmail());
        merchant.setPhone(request.getPhone());
        merchant.setStatus("ACTIVE");
        merchantRepository.save(merchant);

        ApiKey key = new ApiKey();
        key.setMerchantId(merchant.getId());
        key.setApiKey(generator.generateApiKey());
        key.setSecretKey(generator.generateSecretKey());
        key.setActive(true);
        apiKeyRepository.save(key);

        return new MerchantResponse(
                merchant.getMerchantId(),
                key.getApiKey(),
                key.getSecretKey(),
                merchant.getStatus()
        );
    }

    @Override
    public MerchantResponse getMerchant(String merchantId) {
        return null;
    }

    @Override
    public void rotateApiKey(String merchantId) {

    }

}