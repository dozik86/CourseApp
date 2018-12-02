package com.example.manualproject.service;

import com.example.manualproject.model.Rating;
import com.example.manualproject.repository.WorkbookRepository;
import com.example.manualproject.repository.RatingRepository;
import com.example.manualproject.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Transactional
@Component
public class RatingServiceImpl implements RatingService {
    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private WorkbookRepository workbookRepository;

    @Autowired
    private UserRepository userRepository;
//    public List<User> findAllRatedUsers(){
//        List<Rating> ratings = ratingRepository.findAll();
//        List<User> users = new ArrayList<User>();
//        for (Rating rating : ratings) {
//            users.add(rating.getAuthor());
//        }
//        return users;
//    }

    public int getUserRating(long userId, long workbookId) {
        Set<Rating> ratings = workbookRepository.findById(workbookId).getRatings();
        for (Rating rating : ratings) {
            if (rating.getAuthor().getId() == userId) {
                return rating.getValue();
            }
        }
        return -1;
    }

    public void addRating(String json, long workbookId) {
        JSONObject jsonObject = new JSONObject(json);
        long authorId = jsonObject.getLong("author");
        Rating rating = ratingRepository.findByAuthorIdInAndWorkbookId(authorId, workbookId);
        if (rating == null) {
            rating = new Rating();
            rating.setWorkbook(workbookRepository.findById(workbookId));
            rating.setAuthor(userRepository.findById(authorId));
        }
        rating.setValue(jsonObject.getInt("value"));
        ratingRepository.save(rating);
    }
}
