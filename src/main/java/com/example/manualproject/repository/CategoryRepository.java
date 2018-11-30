package com.example.manualproject.repository;

import com.example.manualproject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findById(long id);
}
