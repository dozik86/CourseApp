package com.example.manualproject.controller;

import com.example.manualproject.model.Workbook;
import com.example.manualproject.model.User;
import com.example.manualproject.service.ChangeEmail;
import com.example.manualproject.service.CustomUserDetails;
import com.example.manualproject.service.WorkbookService;
import com.example.manualproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private ChangeEmail changeEmail;

    @ModelAttribute("loggedInUser")
    public User getUser(@AuthenticationPrincipal CustomUserDetails customUser) {
        if (customUser != null) return userService.findById(customUser.getId());
        else return null;
    }

    @RequestMapping(value = "/user/{userId}", method = RequestMethod.GET)
    public ModelAndView showUserPage(@PathVariable long userId) {
        User user = userService.findById(userId);
        Set<Workbook> workbooks = user.getWorkbooks();
        ModelAndView mav = new ModelAndView("user", "user", user);
        mav.addObject("workbooks", user.getWorkbooks());
        return mav;
    }

    @RequestMapping(value = "/user/rename", method = RequestMethod.POST)
    public ResponseEntity rename(@RequestParam(value = "value") String newName, @RequestParam(value = "pk") long id) {
        userService.renameUser(newName, id);
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @RequestMapping(value = "/user/reemail", method = RequestMethod.POST)
    public ResponseEntity reemail(@RequestParam(value = "value") String newEmail, @RequestParam(value = "pk") long id) {
        changeEmail.sendMail(userService.findById(id).getName(), newEmail);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("We've sent message to your email. Check it");
    }

    @RequestMapping(value = "/user/repassword", method = RequestMethod.POST)
    public ResponseEntity repassword(@RequestParam(value = "value") String newPassword, @RequestParam(value = "pk") long id) {
        userService.changePassword(newPassword, id);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password was changed");
    }

    @RequestMapping(value = "/changeemail", method = RequestMethod.GET)
    public String confirmChangingEmail(@RequestParam("u") String user, @RequestParam("h") String hash, @RequestParam("e") String email) {
        userService.changeEmail(email, user, hash);
        long id = userService.findByEmail(email).getId();
        return "redirect:/user/" + id;
    }


}
