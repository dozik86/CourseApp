package com.example.manualproject.service;

import com.example.manualproject.model.Instruction;
import com.example.manualproject.model.User;

import java.util.List;

public interface RatingService {
    int getUserRating(long userId, long instructionId);

    void addRating(String json, long instructionId);

}
