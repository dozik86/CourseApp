package com.example.manualproject.repository;

import com.example.manualproject.model.Rating;
import com.example.manualproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByValue(int value);

    Rating findByAuthorIdInAndWorkbookId(long userId, long workbookId);
}
