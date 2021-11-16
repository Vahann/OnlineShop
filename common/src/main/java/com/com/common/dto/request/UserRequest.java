package com.com.common.dto.request;

import com.com.common.model.enums.Gender;
import com.com.common.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    @Enumerated(EnumType.STRING)
    private Role role;
//    @NotNull
//    @NotEmpty(message = "Name is required")
    private String name;
//    @Max(33)
    private String surname;
//    @Email
//    @NotNull
    private String email;
//    @Size(max = 11, min = 6)
    private String password;

//    @NotEmpty(message = "phone number must start with +")
    private String phoneNumber;
    @Min(3)
    private int age;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private Gender gender;
}
