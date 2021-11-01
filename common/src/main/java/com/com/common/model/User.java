package com.com.common.model;


import com.com.common.model.enums.Gender;
import com.com.common.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

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
    private boolean activeProfile;
    private String token;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

//    @ManyToMany
//    private List<Sale> sale; //kommentic hankarc chhanes!!!


}
