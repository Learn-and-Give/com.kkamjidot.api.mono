package com.kkamjidot.api.mono.repository;

import com.kkamjidot.api.mono.domain.NotificationScheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationSchedulerRepository extends JpaRepository<NotificationScheduler, Long> {
    List<NotificationScheduler> findByNsDayAndNsHour(Integer nsDay, Integer nsHour);

}