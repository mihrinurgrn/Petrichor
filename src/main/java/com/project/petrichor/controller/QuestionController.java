package com.project.petrichor.controller;


import com.project.petrichor.model.Question;
import com.project.petrichor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;



    @RequestMapping(value = {"/questionList/saveQuestion"}, method = RequestMethod.POST)
    public String saveQuestion(@ModelAttribute @Validated Question questionRegister,
                          final RedirectAttributes redirectAttributes) {
        try {
            questionService.save(questionRegister);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
        }

        return "questions";
    }



    @RequestMapping(value = "/voteQuestion/{id}", method = RequestMethod.GET)
    public String findEvent(@PathVariable("id") Integer id,Model model){

        model.addAttribute("question",questionService.findQuestionById(id));
        questionService.voteTheQuestion(id);
        return "questions";
    }





}
