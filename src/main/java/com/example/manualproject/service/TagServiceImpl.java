package com.example.manualproject.service;

import com.example.manualproject.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    public List<String> getTagsList() {
        List<String> tags = new ArrayList<>();
        List<Long> tagsIds = new ArrayList<>();
        List<BigInteger> list = tagRepository.getAllTagIds();
        int n = list.size();
        for (int i = 0; i < n; i++) {
            tagsIds.add(list.get(i).longValue());
        }

        for (Long id : tagsIds) tags.add(tagRepository.findById(id).getName());
        return tags;
    }
}
