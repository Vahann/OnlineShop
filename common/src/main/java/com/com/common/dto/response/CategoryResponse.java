package com.com.common.dto.response;

import com.com.common.model.Category;
import com.com.common.model.enums.ProductForGender;
import com.com.common.model.enums.Size;
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
public class CategoryResponse {

    private int id;
    private String name;
}
