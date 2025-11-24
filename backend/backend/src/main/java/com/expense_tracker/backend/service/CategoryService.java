package com.expense_tracker.backend.service;


import com.expense_tracker.backend.dto.request.CategoryRequest;
import com.expense_tracker.backend.entity.Category;
import com.expense_tracker.backend.entity.User;
import com.expense_tracker.backend.exception.ResourceNotFoundException;
import com.expense_tracker.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserService userService;

    public List<Category> getAllCategories() {
        User currentUser = userService.getCurrentUser();
        return categoryRepository.findByUserId(currentUser.getUserId());
    }

    public List<Category> getCategoriesByType(Category.CategoryType type) {
        User currentUser = userService.getCurrentUser();
        return categoryRepository.findByUserIdAndCategoryType(currentUser.getUserId(), type);
    }

    public Category getCategoryById(Long categoryId) {
        User currentUser = userService.getCurrentUser();
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // Verify category belongs to current user
        if (!category.getUserId().equals(currentUser.getUserId())) {
            throw new RuntimeException("Unauthorized access to category");
        }

        return category;
    }

    public Category createCategory(CategoryRequest request) {
        User currentUser = userService.getCurrentUser();

        Category category = new Category();
        category.setUserId(currentUser.getUserId());
        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setIcon(request.getIcon());
        category.setColor(request.getColor());
        category.setIsDefault(false);

        return categoryRepository.save(category);
    }

    public Category updateCategory(Long categoryId, CategoryRequest request) {
        Category category = getCategoryById(categoryId);

        category.setCategoryName(request.getCategoryName());
        category.setCategoryType(request.getCategoryType());
        category.setIcon(request.getIcon());
        category.setColor(request.getColor());

        return categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        categoryRepository.delete(category);
    }
}
