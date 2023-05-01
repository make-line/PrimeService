package com.example.registrationservice.repo;

import com.example.registrationservice.model.NotifyStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotifyStrategyRepository extends JpaRepository<NotifyStrategy, Long> {
}