package com.project.petrichor.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "events")
public class Event {
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventPasscode() {
        return eventPasscode;
    }

    public void setEventPasscode(String eventPasscode) {
        this.eventPasscode = eventPasscode;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer eventId;

    private String eventName;

    private String eventPasscode;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "event")
    @JsonIgnore
    private List<Question> questions = new ArrayList<>();

    public Event(String eventName, String eventPasscode) {
        this.eventName = eventName;
        this.eventPasscode = eventPasscode;
    }

    public Event() {
    }
}
