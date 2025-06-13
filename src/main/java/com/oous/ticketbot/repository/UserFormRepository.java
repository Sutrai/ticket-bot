package com.oous.ticketbot.repository;

import com.oous.ticketbot.domain.entity.UserForm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFormRepository extends JpaRepository<UserForm, Long> {
    Optional<UserForm> findByUser_TelegramId(Long telegramId);

    Optional<UserForm> findByUser_TelegramIdAndCurrentStepGreaterThan(Long telegramId, Integer currentStep);
}
