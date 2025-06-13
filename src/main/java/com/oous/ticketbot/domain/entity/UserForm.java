package com.oous.ticketbot.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "user_forms")
@Data
public class UserForm {

    @Id
    private Long userId;

    private Integer currentStep = 0;

    private String name;
    private Integer age;
    private String plains;
    private String source;

    public boolean isFormInProgress() {
        return currentStep > 0;
    }
}
