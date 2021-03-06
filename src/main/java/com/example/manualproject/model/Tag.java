package com.example.manualproject.model;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "tags")
@Indexed
public class Tag {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @Field
    private String name;

    @ManyToMany(mappedBy = "tags", cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @IndexedEmbedded(depth = 1)
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
