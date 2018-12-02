package com.example.manualproject.service;

import com.example.manualproject.model.Comment;

import java.util.List;

public interface CommentService {
    void processComment(String json, long workbookId);

    void deleteComment(long id);

    void save(Comment comment);
}
