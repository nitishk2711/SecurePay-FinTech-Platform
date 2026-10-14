
package com.securepay.notification_service.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProviderResponse {

    private boolean success;
    private String provider;
    private String providerMessageId;
    private String errorCode;
    private String errorMessage;
}
