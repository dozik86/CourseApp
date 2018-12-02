package com.example.manualproject.service;

public interface RatingService {
    int getUserRating(long userId, long workbookId);

    void addRating(String json, long workbookId);

}
