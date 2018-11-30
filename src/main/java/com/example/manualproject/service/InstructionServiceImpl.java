package com.example.manualproject.service;

import com.example.manualproject.model.*;
import com.example.manualproject.repository.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.*;

@Transactional
@Component
public class InstructionServiceImpl implements InstructionService {
    @Autowired
    private InstructionRepository instructionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public void processInstruction(String json, long id) {
        JSONObject jsonObject = new JSONObject(json);
        Instruction instruction = new Instruction();
        instruction.setAuthor(userRepository.findById(id));
        instruction.setName(jsonObject.getString("name"));
        instruction.setCategory(categoryRepository.findById(jsonObject.getLong("category")));
        instruction.setSteps(getSteps(jsonObject, instruction));
        instruction.setTags(getTags(jsonObject));
        save(instruction);
    }


    public List<Step> getSteps(JSONObject jsonObject, Instruction instruction) {
        List<Step> steps = new ArrayList<Step>();
        JSONArray ja = jsonObject.getJSONArray("steps");
        int len = ja.length();
        for (int i = 0; i < len; i++) {
            Step step = new Step();
            JSONObject newJsonObject = ja.getJSONObject(i);
            step.setName(newJsonObject.getString("name"));
            step.setNumber(i + 1);
            step.setText(newJsonObject.getString("text"));
            step.setImages(getImages(newJsonObject, step));
            step.setInstruction(instruction);
            steps.add(step);
        }
        return steps;
    }

    public List<Image> getImages(JSONObject jsonObject, Step step) {
        List<Image> images = new ArrayList<Image>();
        JSONArray newJsonArray = jsonObject.getJSONArray("images");
        int length = newJsonArray.length();
        for (int i = 0; i < length; i++) {
            Image image = new Image();
            image.setLink(newJsonArray.getString(i));
            image.setStep(step);
            images.add(image);
        }
        return images;
    }

    public Set<Tag> getTags(JSONObject jsonObject) {
        Set<Tag> tags = new HashSet<Tag>();
        JSONArray ja = jsonObject.getJSONArray("tags");
        int len = ja.length();
        for (int i = 0; i < len; i++) {
            tags.add(checkIfTagPresent(ja, i));
        }
        return tags;
    }

    public Tag checkIfTagPresent(JSONArray ja, int i) {
        Tag tag = new Tag();
        String name = ja.getString(i);
        Tag checkTag = tagRepository.findByName(name);
        if (checkTag == null) tag.setName(name);
        else tag = checkTag;
        return tag;
    }

    public void delete(String[] delArray) {
        long[] idArray = Arrays.asList(delArray).stream().mapToLong(Long::parseLong).toArray();
        for (int i = 0; i < idArray.length; i++)
            instructionRepository.deleteById(idArray[i]);
    }

    public Instruction findById(long id) {
        return instructionRepository.findById(id);
    }

    public void save(Instruction instruction) {
        instructionRepository.save(instruction);
    }

    public List<Instruction> find4LastInstructions() {
        return instructionRepository.findFirst4ByOrderByDateDesc();
    }

    public List<Instruction> find4TopInstructions() {
        List<Instruction> instructions = instructionRepository.findAll();
        Collections.sort(instructions, new Comparator<Instruction>() {
            public int compare(Instruction one, Instruction other) {
                return one.getAverageRating().compareTo(other.getAverageRating());
            }
        });
        return instructions.subList(instructions.size() - 4, instructions.size());
    }

}
