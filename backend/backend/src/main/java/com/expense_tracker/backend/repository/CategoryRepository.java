package com.expense_tracker.backend.repository;


import com.expense_tracker.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByUserId(Long userId);

    List<Category> findByUserIdAndCategoryType(Long userId, Category.CategoryType categoryType);

}
