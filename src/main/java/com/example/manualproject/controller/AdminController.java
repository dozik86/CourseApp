package com.example.manualproject.controller;

import com.example.manualproject.model.User;
import com.example.manualproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
public class AdminController {
    @Autowired
    UserService userService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView admin() {
        List<User> listOfUsers = userService.findAll();
        ModelAndView mav = new ModelAndView("admin");
        mav.addObject("list", listOfUsers);
        return mav;
    }

    @RequestMapping(value = "/admin/action/delete", method = RequestMethod.GET)
    public @ResponseBody
    String delete(@RequestParam(value = "idArray[]") String[] idArray) {
        userService.delete(idArray);
        return "done";
    }

    @RequestMapping(value = "/admin/action/block", method = RequestMethod.GET)
    public @ResponseBody
    String block(@RequestParam(value = "idArray[]") String[] idArray) {
        userService.block(idArray);
        return "done";
    }

    @RequestMapping(value = "/admin/action/unblock", method = RequestMethod.GET)
    public @ResponseBody
    String unblock(@RequestParam(value = "idArray[]") String[] idArray) {
        userService.unblock(idArray);
        return "done";
    }

    @RequestMapping(value = "/admin/action/admin", method = RequestMethod.GET)
    public @ResponseBody
    String makeAdmin(@RequestParam(value = "idArray[]") String[] idArray) {
        userService.addRole(idArray, "ROLE_ADMIN");
        return "done";
    }

    @RequestMapping(value = "/admin/action/disrank", method = RequestMethod.GET)
    public @ResponseBody
    String disrank(@RequestParam(value = "idArray[]") String[] idArray) {
        userService.delRole(idArray, "ROLE_ADMIN");
        return "done";
    }

    @RequestMapping(value = "/admin/reemail", method = RequestMethod.POST)
    public @ResponseBody
    String reemail(@RequestParam(value = "value") String newEmail, @RequestParam(value = "pk") long id) {
        userService.changeEmailByAdmin(newEmail, id);
        return "done";
    }
}
