package com.oous.ticketbot.repository;

import com.oous.ticketbot.domain.entity.UserForm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserFormRepository extends JpaRepository<UserForm, Long> {
}
