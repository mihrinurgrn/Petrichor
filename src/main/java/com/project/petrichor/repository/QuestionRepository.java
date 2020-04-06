package com.project.petrichor.repository;

import com.project.petrichor.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    List<Question> findAll();

    List<Question> findQuestionsByEvent_EventPasscode(String Passcode);

    Question findQuestionByQuestionId(Integer id);


}
