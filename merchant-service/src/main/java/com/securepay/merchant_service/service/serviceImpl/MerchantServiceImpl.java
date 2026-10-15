package com.securepay.merchant_service.service.serviceImpl;

import com.securepay.merchant_service.component.ApiKeyGenerator;
import com.securepay.merchant_service.entity.ApiKey;
import com.securepay.merchant_service.entity.Merchant;
import com.securepay.merchant_service.enums.ApiKeyStatus;
import com.securepay.merchant_service.enums.Environment;
import com.securepay.merchant_service.enums.MerchantStatus;
import com.securepay.merchant_service.exception.MerchantAlreadyExistsException;
import com.securepay.merchant_service.exception.MerchantNotFoundException;
import com.securepay.merchant_service.repository.ApiKeyRepository;
import com.securepay.merchant_service.repository.MerchantRepository;
import com.securepay.merchant_service.request.MerchantRequest;
import com.securepay.merchant_service.request.UpdateMerchantRequest;
import com.securepay.merchant_service.response.MerchantResponse;
import com.securepay.merchant_service.service.MerchantService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final ApiKeyRepository apiKeyRepository;
    private final ApiKeyGenerator generator;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public MerchantResponse onboard(MerchantRequest request) {

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        if (merchantRepository.existsByEmailIgnoreCase(email)) {
            throw new MerchantAlreadyExistsException(email);
        }

        Merchant merchant = new Merchant();

        merchant.setMerchantId(
                "MID" + UUID.randomUUID()
                        .toString()
                        .replace("-", "")
                        .substring(0, 12)
                        .toUpperCase(Locale.ROOT)
        );

        merchant.setBusinessName(request.getBusinessName().trim());
        merchant.setEmail(email);
        merchant.setPhone(request.getPhone().trim());

        // Demo configuration only.
        // Production onboarding should normally begin as PENDING.
        merchant.setStatus(MerchantStatus.ACTIVE);

        merchantRepository.saveAndFlush(merchant);

        String apiKeyValue = generator.generateApiKey();
        String secretValue = generator.generateSecretKey();

        ApiKey key = new ApiKey();
        key.setMerchant(merchant);
        key.setApiKey(apiKeyValue);
        key.setSecretHash(passwordEncoder.encode(secretValue));
        key.setEnvironment(Environment.TEST);
        key.setStatus(ApiKeyStatus.ACTIVE);

        apiKeyRepository.save(key);

        // Return the plaintext secret only once.
        return new MerchantResponse(
                merchant.getMerchantId(),
                apiKeyValue,
                secretValue,
                merchant.getStatus()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public MerchantResponse getMerchant(String merchantId) {

        Merchant merchant = findMerchant(merchantId);

        // Never expose API secrets through merchant retrieval.
        return new MerchantResponse(
                merchant.getMerchantId(),
                null,
                null,
                merchant.getStatus()
        );
    }

    @Override
    @Transactional
    public MerchantResponse updateMerchant(
            String merchantId,
            UpdateMerchantRequest request) {

        Merchant merchant = findMerchant(merchantId);

        String email = request.getEmail()
                .trim()
                .toLowerCase(Locale.ROOT);

        boolean emailUsedByAnotherMerchant =
                merchantRepository.existsByEmailIgnoreCase(email)
                && !merchant.getEmail().equalsIgnoreCase(email);

        if (emailUsedByAnotherMerchant) {
            throw new MerchantAlreadyExistsException(email);
        }

        merchant.setBusinessName(request.getBusinessName().trim());
        merchant.setEmail(email);
        merchant.setPhone(request.getPhone().trim());

        merchantRepository.save(merchant);

        return new MerchantResponse(
                merchant.getMerchantId(),
                null,
                null,
                merchant.getStatus()
        );
    }

    @Override
    @Transactional
    public MerchantResponse updateStatus(
            String merchantId,
            MerchantStatus status) {

        Merchant merchant = findMerchant(merchantId);

        merchant.setStatus(status);
        merchantRepository.save(merchant);

        // In a complete implementation, publish an audit event
        // and enforce allowed state transitions here.

        return new MerchantResponse(
                merchant.getMerchantId(),
                null,
                null,
                merchant.getStatus()
        );
    }

    @Override
    @Transactional
    public void rotateApiKey(String merchantId) {

        Merchant merchant = findMerchant(merchantId);

        ApiKey oldKey = apiKeyRepository
                .findByMerchant_IdAndEnvironment(
                        merchant.getId(),
                        Environment.TEST
                )
                .stream()
                .filter(key -> key.getStatus() == ApiKeyStatus.ACTIVE)
                .findFirst()
                .orElseThrow(() ->
                        new IllegalStateException(
                                "No active TEST API key exists"
                        ));

        oldKey.setStatus(ApiKeyStatus.REVOKED);
        oldKey.setRevokedAt(Instant.now());

        ApiKey newKey = new ApiKey();
        newKey.setMerchant(merchant);
        newKey.setApiKey(generator.generateApiKey());
        newKey.setSecretHash(
                passwordEncoder.encode(generator.generateSecretKey())
        );
        newKey.setEnvironment(Environment.TEST);
        newKey.setStatus(ApiKeyStatus.ACTIVE);

        apiKeyRepository.save(oldKey);
        apiKeyRepository.save(newKey);

        // This method does not return the new secret.
        // Add a dedicated rotation response that returns it once.
    }

    private Merchant findMerchant(String merchantId) {

        return merchantRepository.findByMerchantId(merchantId)
                .orElseThrow(() ->
                        new MerchantNotFoundException(merchantId));
    }
}
