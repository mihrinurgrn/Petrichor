package com.project.petrichor;

import com.project.petrichor.model.Event;
import com.project.petrichor.model.Question;
import com.project.petrichor.repository.QuestionRepository;
import com.project.petrichor.service.Impl.QuestionServiceImp;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class QuestionServiceImpTest {
    private static List<Question> questions;
    private static Event event;
    private static Question question;
    private static Question question2;

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImp questionServiceImp;

    @BeforeAll
    public static void beforeAll(){
        System.out.println("@Before All");
        event = new Event("eventNameDeneme","eventPasscodeDeneme");

        question = new Question("questionText",0, event);
        question2 = new Question("questionText2",0, event);
        event = new Event("eventNameDeneme", "eventPasscodeDeneme");
        questions = new ArrayList<>();
        questions.add(question);
        questions.add(question2);
    }

    @BeforeEach
    public void beforeEach(){
        System.out.println("@BeforeEach");
        assertNotNull(questions);
    }

    @Test
    public void should_find_all_questions(){
        Mockito.when(questionRepository.findAll()).thenReturn(questions);
        assertEquals(questionServiceImp.findAll().size(), questions.size());
    }

    @Test
    public void should_save_question(){
        Question questionSave = new Question("questionTextSave", 0, event);

        Mockito.when(questionRepository.save(questionSave)).thenReturn(questionSave);
        Question result = questionServiceImp.save(questionSave);
        assertEquals(result.getText(), questionSave.getText());
    }

    public void should_return_questions_by_passcode(){

    }
}
