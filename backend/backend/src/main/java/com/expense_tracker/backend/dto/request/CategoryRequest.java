package com.expense_tracker.backend.dto.request;

import com.expense_tracker.backend.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category type is required")
    private Category.CategoryType categoryType;

    private String icon;
    private String color;
}