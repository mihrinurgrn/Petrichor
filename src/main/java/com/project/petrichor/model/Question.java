package com.project.petrichor.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @NotNull
    private String text;

    private Integer voteValue;
    @NotNull
    @ManyToOne
    @JoinColumn(name="eventId")
    private Event event;
}
