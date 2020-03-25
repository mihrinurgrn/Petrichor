package com.project.petrichor.controller;


import com.project.petrichor.model.Event;
import com.project.petrichor.model.Question;
import com.project.petrichor.service.EventService;
import com.project.petrichor.service.QuestionService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"activeEvent","activePasscode"})

public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    EventService eventService;

    @ModelAttribute("activeEvent")
    public Event setUpEvent() {
        return new Event();
    }

    @ModelAttribute("activePasscode")
    public String setUpPasscode() {
        return new String();
    }

    ////////////////////


    @MessageMapping("/question")
    @SendTo("/topic/question")
    public String greeting(String question1,@ModelAttribute("activeEvent")  Event event) throws Exception {

        Thread.sleep(1000); // simulated delay

        Question question=new Question();
        question.setText(question1);
        question.setEvent(event);
        questionService.save(question);

        return question1;
    }

    ////////////////////////
    @GetMapping(value = "/questions2")
    public String findEvent2(Model model,@ModelAttribute("activePasscode") String passcode2
            ,@ModelAttribute("activeEvent") Event event) {
            passcode2=event.getEventPasscode();
            List<Question> questions=questionService.findQuestionsByPasscode(passcode2);
            model.addAttribute("questions",questions);
            Question questionRegister = new Question();
            model.addAttribute("questionRegister",questionRegister);
            return "questions";
    }



    @GetMapping(value = "/questions")
    public String findEvent(@RequestParam(value="passcode",required=false) String passcode1
            , Model model, @ModelAttribute("activePasscode") String passcode2,
                            final RedirectAttributes redirectAttributes) {
        if(passcode1==null)
        {
            return "redirect:/questions2";
        }
        Event event=eventService.findByPassCode(passcode1);
        model.addAttribute("activeEvent",eventService.findByPassCode(passcode1));

        if(passcode1==null && passcode2==null) {
            redirectAttributes.addFlashAttribute("msg","Lütfen bir passcode giriniz");
            return "redirect:/passcode";
        }

        if(event==null)
        {
            redirectAttributes.addFlashAttribute("msg","Böyle bir passcode bulunmamaktadır");
            return "redirect:/passcode";
        }

        if(passcode2.isEmpty()!=false)
        {
            model.addAttribute("activeEvent",eventService.findByPassCode(passcode1));
            List<Question> questions=questionService.findQuestionsByPasscode(passcode1);
           /* questionService.orderByVoteNumber(questions);*/
            model.addAttribute("questions",questions);
        }
        if(passcode2.isEmpty()==false && passcode1!=null)
        {
            model.addAttribute("activeEvent",eventService.findByPassCode(passcode1));
            List<Question> questions=questionService.findQuestionsByPasscode(passcode1);
         /*   questionService.orderByVoteNumber(questions);*/
            model.addAttribute("questions",questions);
        }
        Question questionRegister = new Question();
        model.addAttribute("questionRegister",questionRegister);
        return "questions";
   }


    @RequestMapping(value = "/questionList/saveQuestion" , method = RequestMethod.POST)
    public String saveQuestion(@ModelAttribute @Validated Question questionRegister,
                               @ModelAttribute("activeEvent")  Event event, Model model,
                               final RedirectAttributes redirectAttributes) {


        try {
            questionRegister.setEvent(event);
            System.out.println("deneme-sdcfvbnmdcfvbnmvbnmn//////******");
            questionService.save(questionRegister);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
        }

        return "redirect:/questions";

    }



    @RequestMapping(value = "/voteQuestion/{id}", method = RequestMethod.GET)
    public String findEvent(@PathVariable("id") Integer id,HttpServletRequest request,
    HttpServletResponse response,
    @CookieValue(value = "vote", defaultValue = "1") String voteValue,
    @CookieValue(value = "question", defaultValue = "null") String questionId) {
        Cookie[] cookies=request.getCookies();

        int k;
        for(k=0;k<cookies.length;k++)
        {
            if(cookies[k].getValue().equals(id.toString())) {
                return "redirect:/questions";
            }
        }
        questionService.voteTheQuestion(id);
        Cookie cookie=questionService.createCookie(id.toString(),id.toString());
        response.addCookie(cookie);
        return "redirect:/questions";
    }







//    @RequestMapping(value = "/voteQuestion/{id}", method = RequestMethod.GET)
//    public String findEvent(@PathVariable("id") Integer id,HttpServletRequest request,
//                            HttpServletResponse response,
//                            @CookieValue(value = "vote", defaultValue = "1") String voteValue,
//                            @CookieValue(value = "question", defaultValue = "null") String questionId) {
//        Cookie[] cookies=request.getCookies();
//
//        int k;
//        for(k=0;k<cookies.length;k++)
//        {
//            cookies[k].setMaxAge(0);
//            response.addCookie(cookies[k]);
//
////            if(cookies[k].getValue().equals(id.toString())) {
////                return "redirect:/questions";
////            }
//        }
//        questionService.voteTheQuestion(id);
//        Cookie cookie=questionService.createCookie(id.toString(),id.toString());
//        response.addCookie(cookie);
//        return "redirect:/questions";
//    }
//


    @RequestMapping(value = "/exit", method = RequestMethod.GET)
    public String exit(SessionStatus status){
        status.setComplete();
        return "home";
    }



}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////