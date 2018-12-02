package com.example.manualproject.controller;

import com.example.manualproject.model.User;
import com.example.manualproject.repository.UserRepository;
import com.example.manualproject.service.UserService;
import com.example.manualproject.service.social.FBAuth;
import com.example.manualproject.service.social.TwitterAuth;
import com.example.manualproject.service.social.VKAuth;
import com.example.manualproject.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class SecurityController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private FBAuth fbAuth;

    @Autowired
    private VKAuth vkAuth;

    @Autowired
    private TwitterAuth twAuth;

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLoginForm(@RequestParam(required = false) String isConfirmed) {
        ModelAndView mav = new ModelAndView("login", "action", "login");
        if (isConfirmed != null) {
            if (isConfirmed.equals("true")) mav.addObject("confirm", true);
            else mav.addObject("confirm", false);
        }
        return mav;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegisterForm() {
        return new ModelAndView("login", "action", "register");
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@ModelAttribute User user, BindingResult bindingResult) {
        ModelAndView mav = new ModelAndView("login", "action", "register");
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) return mav;
        userService.save(user);
        mav.addObject("registration", true);
        return mav;
    }

    @RequestMapping(value = "/registrationconfirm", method = RequestMethod.GET)
    public String confirm(@RequestParam("u") String user, @RequestParam("h") String hash) {
        return "redirect:/login?isConfirmed=" + userService.confirm(user, hash);
    }

    @RequestMapping(value = "/login-error")
    public ModelAndView loginerror() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("action", "login");
        mav.addObject("loginError", true);
        return mav;
    }

    @RequestMapping(value = "/auth/vk", method = RequestMethod.GET)
    public String vkAuth(@RequestParam(value = "code", required = false) String code) {
        if (code == null)
            vkAuth.getCode();
        else {
            vkAuth.auth(code);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/auth/facebook", method = RequestMethod.GET)
    public String fbAuth(@RequestParam(value = "code", required = false) String code) {
        if (code == null)
            fbAuth.getCode();
        else {
            fbAuth.auth(code);
        }
        return "redirect:/";
    }

    @RequestMapping(value = "/auth/twitter", method = RequestMethod.GET)
    public String twAuth(@RequestParam(value = "oauth_verifier", required = false) String verifier) {
        if (verifier == null)
            twAuth.getCallback();
        else {
            twAuth.auth(verifier);
        }
        return "redirect:/";
    }
}
