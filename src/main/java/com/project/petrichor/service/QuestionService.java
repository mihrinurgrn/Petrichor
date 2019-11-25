package com.project.petrichor.service;

import com.project.petrichor.model.Question;

import javax.servlet.http.Cookie;
import java.util.List;

public interface QuestionService {
    Question save(Question question);

    List<Question> findAll();

    List<Question> findQuestionsByPasscode(String passcode);

    void voteTheQuestion(Integer id);

    Cookie createCookie(String name,String value);



    Question findQuestionById(int id);

}
