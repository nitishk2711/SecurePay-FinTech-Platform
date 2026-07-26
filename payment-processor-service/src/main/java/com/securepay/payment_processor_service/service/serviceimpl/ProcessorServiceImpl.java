package com.securepay.payment_processor_service.service.serviceimpl;

import com.securepay.payment_processor_service.request.ProcessorRequest;
import com.securepay.payment_processor_service.response.ProcessorResponse;
import com.securepay.payment_processor_service.service.ProcessorService;
import org.springframework.stereotype.Service;


@Service
public class ProcessorServiceImpl implements ProcessorService {


    @Override
    public ProcessorResponse processPayment(ProcessorRequest request) {

        boolean success = Math.random() > 0.2;

        if (success) {
            return new ProcessorResponse(
                    request.getPaymentId(),
                    "SUCCESS",
                    "Payment completed successfully"
            );
        }


        return new ProcessorResponse(
                request.getPaymentId(),
                "FAILED",
                "Bank declined transaction"
        );
    }
}
