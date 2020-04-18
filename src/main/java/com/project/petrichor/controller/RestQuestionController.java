package com.project.petrichor.controller;

import com.project.petrichor.model.MyResponse;
import com.project.petrichor.model.Question;
import com.project.petrichor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestQuestionController {
    @Autowired
    QuestionService questionService;

}
