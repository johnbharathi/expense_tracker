package com.expense_tracker.backend.dto.request;

import com.expense_tracker.backend.entity.Category;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryRequest {

    public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Category.CategoryType getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(Category.CategoryType categoryType) {
		this.categoryType = categoryType;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	@NotBlank(message = "Category name is required")
    private String categoryName;

    @NotNull(message = "Category type is required")
    private Category.CategoryType categoryType;

    private String icon;
    private String color;
}