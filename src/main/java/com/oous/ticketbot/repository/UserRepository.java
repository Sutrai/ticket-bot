package com.oous.ticketbot.repository;

import com.oous.ticketbot.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByTelegramId(Long telegramId);

    Optional<Object> findByTelegramId(Long id);
}
