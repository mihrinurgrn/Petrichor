package com.project.petrichor.controller;


import com.project.petrichor.model.Event;
import com.project.petrichor.model.Question;
import com.project.petrichor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller

public class MainController {
    @Autowired
    QuestionService questionService;
    @GetMapping("/")
    public String root(HttpServletRequest request,Model model) {
        try {
            String f=request.getSession().getAttribute("activeEvent").toString();
        }
        catch(Exception e) {
            return "home";
        }
        Event event= (Event) request.getSession().getAttribute("activeEvent");

        List<Question> questions = questionService.findQuestionsByPasscode(event.getEventPasscode());

        model.addAttribute("questions", questions);
        Question questionRegister = new Question();
        model.addAttribute("questionRegister", questionRegister);
        return "questions";

    }


    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/passcode")
    public String passcode() {
        return "passcode";
    }


}
