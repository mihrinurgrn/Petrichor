package com.project.petrichor.service;

import com.project.petrichor.model.Event;

import java.util.List;

public interface EventService {
    Event save(Event event);

    Event findByPassCode(String pascode);

    List<Event> findAll();
}
