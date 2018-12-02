package com.example.manualproject.service;

import com.example.manualproject.model.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

public interface WorkbookService {
    void save(Workbook workbook);

    void processWorkbook(String json, long id);

    public List<Image> getImages(JSONObject jsonObject, Question question);

    List<Question> getQuestions(JSONObject jsonObject, Workbook workbook);

    Set<Tag> getTags(JSONObject jsonObject);

    Tag checkIfTagPresent(JSONArray ja, int i);

    void delete(String[] delArray);

    Workbook findById(long id);

    List<Workbook> find4LastWorkbooks();

    List<Workbook> find4TopWorkbooks();
}

