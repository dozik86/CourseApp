package com.example.manualproject.service;

import com.example.manualproject.model.*;
import com.example.manualproject.repository.*;
import org.hibernate.SessionFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional
@Component
public class WorkbookServiceImpl implements WorkbookService {
    @Autowired
    private WorkbookRepository workbookRepository;

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

    public void processWorkbook(String json, long id) {
        JSONObject jsonObject = new JSONObject(json);
        Workbook workbook = new Workbook();
        workbook.setAuthor(userRepository.findById(id));
        workbook.setName(jsonObject.getString("name"));
        workbook.setCategory(categoryRepository.findById(jsonObject.getLong("category")));
        workbook.setQuestions(getQuestions(jsonObject, workbook));
        workbook.setTags(getTags(jsonObject));
        save(workbook);
    }


    public List<Question> getQuestions(JSONObject jsonObject, Workbook workbook) {
        List<Question> questions = new ArrayList<Question>();
        JSONArray ja = jsonObject.getJSONArray("questions");
        int len = ja.length();
        for (int i = 0; i < len; i++) {
            Question question = new Question();
            JSONObject newJsonObject = ja.getJSONObject(i);
            question.setName(newJsonObject.getString("name"));
            question.setNumber(i + 1);
            question.setText(newJsonObject.getString("text"));
            question.setImages(getImages(newJsonObject, question));
            question.setWorkbook(workbook);
            questions.add(question);
        }
        return questions;
    }

    public List<Image> getImages(JSONObject jsonObject, Question question) {
        List<Image> images = new ArrayList<Image>();
        JSONArray newJsonArray = jsonObject.getJSONArray("images");
        int length = newJsonArray.length();
        for (int i = 0; i < length; i++) {
            Image image = new Image();
            image.setLink(newJsonArray.getString(i));
            image.setQuestion(question);
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
            workbookRepository.deleteById(idArray[i]);
    }

    public Workbook findById(long id) {
        return workbookRepository.findById(id);
    }

    public void save(Workbook workbook) {
        workbookRepository.save(workbook);
    }

    public List<Workbook> find4LastWorkbooks() {
        return workbookRepository.findFirst4ByOrderByDateDesc();
    }

    public List<Workbook> find4TopWorkbooks() {
        List<Workbook> workbooks = workbookRepository.findAll();
        Collections.sort(workbooks, new Comparator<Workbook>() {
            public int compare(Workbook one, Workbook other) {
                return one.getAverageRating().compareTo(other.getAverageRating());
            }
        });
        return workbooks.subList(workbooks.size() - 4, workbooks.size());
    }

}
