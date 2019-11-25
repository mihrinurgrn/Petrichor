package com.project.petrichor.service.Impl;

import com.project.petrichor.model.Question;
import com.project.petrichor.repository.QuestionRepository;
import com.project.petrichor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    QuestionRepository questionRepository;


    @Override
    public Question save(Question question) {
        return questionRepository.save(question);
    }

    @Override
    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    @Override
    public List<Question> findQuestionsByPasscode(String passcode) {
        return questionRepository.findQuestionsByEvent_EventPasscode(passcode);
    }


    @Override
    public void voteTheQuestion(Integer id) {
        Question question = questionRepository.findQuestionByQuestionId(id);
        question.setVoteValue(question.getVoteValue()+1);
        questionRepository.save(question);
    }
    @Override
    public Cookie createCookie(String name, String value) {
        Cookie cookie=new Cookie(name,value);
        return cookie;
    }

    @Override
    public Question findQuestionById(int id) {
        return questionRepository.findQuestionByQuestionId(id);
    }
}