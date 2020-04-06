package com.project.petrichor.model;

import com.sun.istack.NotNull;


import javax.persistence.*;

@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer questionId;

    @NotNull
    private String text;

    private Integer voteValue = 0;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "eventId")
    private Event event;

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getVoteValue() {
        return voteValue;
    }

    public void setVoteValue(Integer voteValue) {
        this.voteValue = voteValue;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Question(String text, Integer voteValue, Event event) {
        this.text = text;
        this.voteValue = voteValue;
        this.event = event;
    }

    public Question() {
    }
}
