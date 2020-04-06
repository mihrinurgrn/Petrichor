package com.project.petrichor;

import com.project.petrichor.model.Event;
import com.project.petrichor.repository.EventRepository;
import com.project.petrichor.service.Impl.EventServiceImp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class EventServiceImpTest {

    private static List<Event> events;
    private static Event event;
    private static Event event2;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventServiceImp eventServiceImp;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("@Before All");

        event = new Event("eventNameDeneme", "eventPasscodeDeneme");
        event2 = new Event("eventNameDeneme2", "eventPasscodeDeneme2");
        events = new ArrayList<>();
        events.add(event);
        events.add(event2);
    }

    @BeforeEach
    public void beforeEach() {
        System.out.println("@BeforeEach");
        assertNotNull(events);
    }

    @Test
    public void should_find_all_events() {
        Mockito.when(eventRepository.findAll()).thenReturn(events);
        assertEquals(eventServiceImp.findAll().size(), events.size());
    }

    @Test
    public void should_save_event() {
        Event event = new Event("eventNameSave", "eventPasscodeSave");

        Mockito.when(eventRepository.save(event)).thenReturn(event);
        Event result = eventServiceImp.save(event);
        assertEquals(result.getEventName(), event.getEventName());
    }

    @Test
    public void should_return_passcode() {
        Event event = new Event("eventName", "eventPasscode");
        Mockito.when(eventRepository.findByEventPasscode("eventPasscode")).thenReturn(event);
        assertEquals(eventServiceImp.findByPassCode("eventPasscode"), event);
    }
}
