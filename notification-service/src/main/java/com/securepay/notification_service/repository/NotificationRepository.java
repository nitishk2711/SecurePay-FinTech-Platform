package com.securepay.notification_service.repository;

import com.securepay.notification_service.entity.Notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationRepository
        extends MongoRepository<Notification, String> {

    // Get user notifications, newest first, with pagination
    Page<Notification> findByUserIdOrderByCreatedAtDesc(
            String userId,
            Pageable pageable
    );

    // Find a notification by event ID
    Optional<Notification> findByEventId(String eventId);

    // Check whether a notification already exists for an event
    boolean existsByEventId(String eventId);
}