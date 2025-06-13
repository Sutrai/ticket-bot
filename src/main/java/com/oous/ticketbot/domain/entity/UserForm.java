package com.oous.ticketbot.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "user_forms")
@Data
public class UserForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private Integer currentStep = 0;

    private String name;
    private Integer age;
    private String plains;
    private String source;

    public boolean isFormInProgress() {
        return currentStep > 0;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
