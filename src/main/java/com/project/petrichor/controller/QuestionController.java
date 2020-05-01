package com.project.petrichor.controller;


import com.project.petrichor.model.*;
import com.project.petrichor.service.EventService;
import com.project.petrichor.service.QuestionService;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes({"activeEvent", "activePasscode"})

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

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting( HtmlUtils.htmlEscape(message.getName()) );
    }


    @MessageMapping("/vote")
    @SendTo("/topic/votes")
    public String vote(String qId) throws Exception {
        Thread.sleep(1000); // simulated delay
        return qId;
    }

/*
    @GetMapping(value = "/questions2")
    public String findEvent2(Model model, @ModelAttribute("activePasscode") String passcode2
            , @ModelAttribute("activeEvent") Event event) {
        passcode2 = event.getEventPasscode();
        List<Question> questions = questionService.findQuestionsByPasscode(passcode2);
        model.addAttribute("questions", questions);
        Question questionRegister = new Question();
        model.addAttribute("questionRegister", questionRegister);
        return "questions";
    }*/

   /* @GetMapping(value = "/questions2")
    public String findEvent2(HttpServletRequest request,Model model) {


        Event event= (Event) request.getSession().getAttribute("activeEvent");

        List<Question> questions = questionService.findQuestionsByPasscode(event.getEventPasscode());

        model.addAttribute("questions", questions);
        Question questionRegister = new Question();
        model.addAttribute("questionRegister", questionRegister);
        return "questions";
    }*/



    @GetMapping(value = "/questions")
    public String findEvent(@RequestParam(value = "passcode", required = false) String passcode1
            , Model model, @ModelAttribute("activePasscode") String passcode2,
                            final RedirectAttributes redirectAttributes) {
        if (passcode1 == null) {
            return "redirect:/questions2";
        }
        Event event = eventService.findByPassCode(passcode1);
        model.addAttribute("activeEvent", eventService.findByPassCode(passcode1));

        if (passcode1 == null && passcode2 == null) {
            redirectAttributes.addFlashAttribute("msg", "Lütfen bir passcode giriniz");
            return "redirect:/passcode";
        }

        if (event == null) {
            redirectAttributes.addFlashAttribute("msg", "Böyle bir passcode bulunmamaktadır");
            return "redirect:/passcode";
        }

        if (passcode2.isEmpty() != false) {
            model.addAttribute("activeEvent", eventService.findByPassCode(passcode1));
            List<Question> questions = questionService.findQuestionsByPasscode(passcode1);
            /* questionService.orderByVoteNumber(questions);*/
            model.addAttribute("questions", questions);
        }
        if (passcode2.isEmpty() == false && passcode1 != null) {
            model.addAttribute("activeEvent", eventService.findByPassCode(passcode1));
            List<Question> questions = questionService.findQuestionsByPasscode(passcode1);
            /*   questionService.orderByVoteNumber(questions);*/
            model.addAttribute("questions", questions);
        }
        Question questionRegister = new Question();
        model.addAttribute("questionRegister", questionRegister);

        return "questions";
    }

/*    @GetMapping("/example")
    public ResponseEntity<Object> getQuestions() {
        MyResponse<List<Question>> response = new MyResponse<>("success", questionService.findAll());
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }*/

    @GetMapping("/example")
    public ResponseEntity<AjaxResponseBody> getQuestions(Model model, @ModelAttribute("activePasscode") String passcode2
            , @ModelAttribute("activeEvent") Event event) {

        passcode2 = event.getEventPasscode();
        List<Question> questions = questionService.findQuestionsByPasscode(passcode2);
        model.addAttribute("questions", questions);
        Question questionRegister = new Question();
        model.addAttribute("questionRegister", questionRegister);


        AjaxResponseBody result = new AjaxResponseBody();
        result.setResult(questions);
        result.setMsg("ok");
        return ResponseEntity.ok(result);
    }

    @PostMapping("/questionList/saveQuestion")
    public @ResponseBody
    ResponseEntity<AjaxResponseBody> saveQuestion(@Valid @RequestBody Question questionRegister,
                                                  @ModelAttribute("activeEvent") Event event, Model model, Errors errors,
                                                  final RedirectAttributes redirectAttributes) {
         try {
            questionRegister.setEvent(event);
            System.out.println("deneme-sdcfvbnmdcfvbnmvbnmn//////******");
            questionService.save(questionRegister);
            redirectAttributes.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("msg", "fail");
        }

        AjaxResponseBody result = new AjaxResponseBody();
         if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));
            return ResponseEntity.badRequest().body(result);

        }
            return ResponseEntity.ok(result);
   }

    @PostMapping("/vote")
    public @ResponseBody
    ResponseEntity<AjaxResponseBody> findEvent(@Valid @RequestBody Question question, HttpServletRequest request,
                            HttpServletResponse response,
                            @CookieValue(value = "vote", defaultValue = "1") String voteValue,
                            @CookieValue(value = "question", defaultValue = "null") String questionId) {
        Cookie[] cookies = request.getCookies();
        request.getSession();
        AjaxResponseBody result = new AjaxResponseBody();

        int id=question.getQuestionId();
        question=questionService.findQuestionById(id);
        System.out.println(question.toString());


        int k;
        for (k = 0; k < cookies.length; k++) {

            if (cookies[k].getName().equals(question.getQuestionId().toString())) {
                return ResponseEntity.ok(result);
            }
        }
        questionService.voteTheQuestion(question.getQuestionId());
        Cookie cookie = questionService.createCookie(String.valueOf(id), String.valueOf(id));
        response.addCookie(cookie);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(value = "/voteQuestion/{id}", method = RequestMethod.GET)
    public String findEvent(@PathVariable("id") Integer id, HttpServletRequest request,
                            HttpServletResponse response,
                            @CookieValue(value = "vote", defaultValue = "1") String voteValue,
                            @CookieValue(value = "question", defaultValue = "null") String questionId) {
        Cookie[] cookies = request.getCookies();

        int k;
        for (k = 0; k < cookies.length; k++) {
            if (cookies[k].getValue().equals(id.toString())) {
                return "redirect:/questions";
            }
        }
        questionService.voteTheQuestion(id);
        Cookie cookie = questionService.createCookie(id.toString(), id.toString());
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
    public String exit(SessionStatus status) {
        status.setComplete();
        return "home";
    }


}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////