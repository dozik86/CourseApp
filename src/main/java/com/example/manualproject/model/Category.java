package com.example.manualproject.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "categories")
@Indexed
public class Category {
    @Id
    @GeneratedValue
    private Long id;
    @Field
    private String name;

    @IndexedEmbedded(depth = 1)
    @OneToMany(mappedBy = "category")
    private Set<Workbook> workbooks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Workbook> getWorkbooks() {
        return workbooks;
    }

    public void setWorkbooks(Set<Workbook> workbooks) {
        this.workbooks = workbooks;
    }
}
