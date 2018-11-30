package com.example.manualproject.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue
    private long id;
    private String link;

    @ManyToOne
    @JoinColumn(name = "stepid", referencedColumnName = "id")
    private Step step;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }
}
