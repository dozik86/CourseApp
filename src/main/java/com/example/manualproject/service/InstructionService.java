package com.example.manualproject.service;

import com.example.manualproject.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface InstructionService {
    void save(Instruction instruction);

    void processInstruction(String json, long id);

    public List<Image> getImages(JSONObject jsonObject, Step step);

    List<Step> getSteps(JSONObject jsonObject, Instruction instruction);

    Set<Tag> getTags(JSONObject jsonObject);

    Tag checkIfTagPresent(JSONArray ja, int i);

    void delete(String[] delArray);

    Instruction findById(long id);

    List<Instruction> find4LastInstructions();

    List<Instruction> find4TopInstructions();
}

