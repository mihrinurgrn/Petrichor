package com.project.petrichor.controller;

import com.project.petrichor.model.Event;
import com.project.petrichor.model.Question;
import com.project.petrichor.service.EventService;
import com.project.petrichor.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    QuestionService questionService;


    @GetMapping("/addEvent")
    public String addEvent(Model model) {
        Event eventRegister = new Event();
        model.addAttribute("eventRegister",eventRegister);
        return "addEvent";
    }

    @RequestMapping(value = "/eventSave", method = RequestMethod.POST)
    public String eventSave(@ModelAttribute @Validated Event eventRegister,
                            final RedirectAttributes redirectAttributes) {
        try {
            eventService.save(eventRegister);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
        }

        return "redirect:/home";
    }



    @GetMapping(value = "/findEvent")
    public String findEventt(@RequestParam(value="passcode",required=false) String passcode) {
        if(passcode==null)
            return "passcode";
        Event event=eventService.findByPassCode(passcode);
        List<Question> questions=questionService.findQuestionsByPasscode(passcode);
        return "questions";
    }













}