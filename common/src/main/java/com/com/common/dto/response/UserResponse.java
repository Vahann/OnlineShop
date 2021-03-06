package com.com.common.dto.response;

import com.com.common.model.Product;
import com.com.common.model.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {

    private int id;
    private String name;
    private String surname;
    private String email;
    private int age;
    private boolean isActive;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private List<Product> products;
}
