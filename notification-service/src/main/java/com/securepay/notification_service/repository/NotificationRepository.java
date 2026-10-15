
package com.securepay.notification_service.repository;

import com.securepay.notification_service.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NotificationRepository
        extends JpaRepository<Notification, String> {

    Page<Notification> findByUserIdOrderByCreatedAtDesc(
            String userId,
            Pageable pageable
    );

    Optional<Notification> findByEventId(String eventId);

    boolean existsByEventId(String eventId);
}
