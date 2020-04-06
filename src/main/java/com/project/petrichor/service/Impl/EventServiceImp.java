package com.project.petrichor.service.Impl;

import com.project.petrichor.model.Event;
import com.project.petrichor.repository.EventRepository;
import com.project.petrichor.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventServiceImp implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Override
    public Event save(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event findByPassCode(String passcode) {
        return eventRepository.findByEventPasscode(passcode);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }
}
