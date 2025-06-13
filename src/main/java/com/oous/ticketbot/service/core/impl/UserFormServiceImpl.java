package com.oous.ticketbot.service.core.impl;

import com.oous.ticketbot.domain.entity.UserForm;
import com.oous.ticketbot.repository.UserFormRepository;
import com.oous.ticketbot.service.core.UserFormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserFormServiceImpl implements UserFormService {
    private final UserFormRepository userFormRepository;

    public UserForm findByUserId(Long userId) {
        return userFormRepository.findById(userId).orElse(null);
    }

    public void save(UserForm userForm) {
        userFormRepository.save(userForm);
    }
}
