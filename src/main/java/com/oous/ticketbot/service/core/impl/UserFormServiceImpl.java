package com.oous.ticketbot.service.core.impl;

import com.oous.ticketbot.domain.entity.UserForm;
import com.oous.ticketbot.repository.UserFormRepository;
import com.oous.ticketbot.service.core.UserFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFormServiceImpl implements UserFormService {
    private final UserFormRepository userFormRepository;

    public UserForm findByUserId(Long userId) {
        return userFormRepository.findByUser_TelegramId(userId)
                .orElse(null);
    }

    public void save(UserForm userForm) {
        userFormRepository.save(userForm);
    }

    public UserForm findActiveFormByUserId(Long userId) {
        return userFormRepository.findByUser_TelegramIdAndCurrentStepGreaterThan(userId, 0)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public UserForm findById(Long id) {
        return userFormRepository.findById(id).orElse(null);
    }
}
