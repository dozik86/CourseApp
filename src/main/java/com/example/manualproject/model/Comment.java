package com.example.manualproject.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;

@Entity
@Table(name = "comments")
@Indexed
public class Comment {
    @Id
    @GeneratedValue
    private long id;

    @Field
    private String text;

    @ManyToOne
    @JoinColumn(name = "authorid", referencedColumnName = "id")
    @IndexedEmbedded
    private User author;

    @ManyToOne
    @JoinColumn(name = "workbookid", referencedColumnName = "id")
    @IndexedEmbedded
    private Workbook workbook;

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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }

}
