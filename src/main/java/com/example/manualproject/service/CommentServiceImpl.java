package com.example.manualproject.service;

import com.example.manualproject.model.Comment;
import com.example.manualproject.repository.CommentRepository;
import com.example.manualproject.repository.InstructionRepository;
import com.example.manualproject.repository.UserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Component
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private InstructionRepository instructionRepository;

    @Autowired
    private UserRepository userRepository;

    public void processComment(String json, long instructionId) {
        JSONObject jsonObject = new JSONObject(json);
        Comment comment = new Comment();
        comment.setText(jsonObject.getString("text"));
        comment.setInstruction(instructionRepository.findById(instructionId));
        comment.setAuthor(userRepository.findById(Long.parseLong(jsonObject.getString("author"))));
        save(comment);
    }

    public void deleteComment(long id) {
        commentRepository.deleteById(id);
    }

    public void save(Comment comment) {
        commentRepository.save(comment);
    }
}
