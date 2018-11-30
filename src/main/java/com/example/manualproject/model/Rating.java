package com.example.manualproject.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    @Range(min = 1, max = 5)
    private int value;

    @ManyToOne(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinColumn(name = "instructionid", referencedColumnName = "id")
    private Instruction instruction;

    @ManyToOne(cascade =
            {
                    CascadeType.DETACH,
                    CascadeType.MERGE,
                    CascadeType.REFRESH,
                    CascadeType.PERSIST
            })
    @JoinColumn(name = "authorid", referencedColumnName = "id")
    private User author;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }
}
