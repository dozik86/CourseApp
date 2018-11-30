package com.example.manualproject.repository;

import com.example.manualproject.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteById(long id);
}
