package com.example.manualproject.repository;

import com.example.manualproject.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);

    Tag findById(Long id);

    @Query(value = "SELECT tag_id FROM workbook_tag", nativeQuery = true)
    List<BigInteger> getAllTagIds();
}
