package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.NotificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotiTokenRepository extends JpaRepository<NotificationToken, Long> {
}