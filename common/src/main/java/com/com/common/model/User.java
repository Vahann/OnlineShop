package com.com.common.model;

import com.com.common.model.enums.Gender;
import com.com.common.model.enums.Role;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private Role role;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String phoneNumber;
    private boolean isActive;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;
}
