package com.com.common.dto;

import com.com.common.model.enums.Gender;
import com.com.common.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSaveDto {
    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String surname;
    private String password;
    private String phoneNumber;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
