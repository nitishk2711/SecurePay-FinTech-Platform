package com.securepay.order_service.component;

import com.securepay.order_service.enums.OrderStatus;
import com.securepay.order_service.exception.InvalidOrderStateException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Component
public class OrderStateMachine {

    private static final Map<OrderStatus, Set<OrderStatus>>
        TRANSITIONS = Map.of(
            OrderStatus.CREATED,
                Set.of(
                    OrderStatus.PENDING,
                    OrderStatus.CANCELLED,
                    OrderStatus.EXPIRED
                ),
            OrderStatus.PENDING,
                Set.of(
                    OrderStatus.PROCESSING,
                    OrderStatus.CANCELLED,
                    OrderStatus.EXPIRED,
                    OrderStatus.FAILED
                ),
            OrderStatus.PROCESSING,
                Set.of(
                    OrderStatus.PAID,
                    OrderStatus.FAILED
                ),
            OrderStatus.PAID,
                Set.of(OrderStatus.REFUND_PENDING),
            OrderStatus.REFUND_PENDING,
                Set.of(
                    OrderStatus.PAID,
                    OrderStatus.PARTIALLY_REFUNDED,
                    OrderStatus.REFUNDED
                ),
            OrderStatus.PARTIALLY_REFUNDED,
                Set.of(OrderStatus.REFUND_PENDING,
                        OrderStatus.REFUNDED),
            OrderStatus.FAILED, Set.of(),
            OrderStatus.CANCELLED, Set.of(),
            OrderStatus.EXPIRED, Set.of(),
            OrderStatus.REFUNDED, Set.of()
        );

    public void validate(
            OrderStatus current,
            OrderStatus next) {

        if (!TRANSITIONS
                .getOrDefault(current, Set.of())
                .contains(next)) {

            throw new InvalidOrderStateException(
                "Cannot change order status from "
                    + current + " to " + next
            );
        }
    }
}
