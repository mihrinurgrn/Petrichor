package com.project.petrichor.repository;



import com.project.petrichor.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Integer> {
    List<Event> findAll();
    Event findByEventPasscode(String passcode);
}
