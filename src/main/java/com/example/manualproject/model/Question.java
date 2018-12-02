package com.example.manualproject.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table(name = "questions")
@Indexed
public class Question {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Field
    private String name;
    @NotNull
    @Column(length = 10000)
    @Field
    private String text;

    private int number;

    @ManyToOne
    @JoinColumn(name = "workbookid", referencedColumnName = "id")
    @IndexedEmbedded
    private Workbook workbook;

    @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
    List<Image> images;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }


    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}
