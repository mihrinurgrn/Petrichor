package com.project.petrichor.controller;

import com.project.petrichor.model.Event;
import com.project.petrichor.model.Question;
import com.project.petrichor.service.EventService;
import com.project.petrichor.service.QuestionService;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@SessionAttributes("activeEvent")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    QuestionService questionService;


    @GetMapping("/event")
    public String addEvent(Model model) {
        Event eventRegister = new Event();
        model.addAttribute("eventRegister",eventRegister);
        return "event";
    }

    @RequestMapping(value = "/eventSave", method = RequestMethod.POST)
    public String eventSave(@ModelAttribute @Validated Event eventRegister,
                            final RedirectAttributes redirectAttributes,Model model) {
        try {
          List<Event> EventList=eventService.findAll();

              int i = 0;
              for (i = 0; i < EventList.size(); i++) {

                  if (EventList.get(i).getEventPasscode().equals(eventRegister.getEventPasscode())) {
                      throw new IllegalArgumentException("Bu passcode ile daha önce bir event olusturuldu.");
                  }
              }

            eventService.save(eventRegister);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
            return "redirect:/event";
        }

        return "redirect:/home";
    }


















}