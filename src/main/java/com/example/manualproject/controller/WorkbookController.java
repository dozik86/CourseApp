package com.example.manualproject.controller;

import com.example.manualproject.model.Category;
import com.example.manualproject.model.Workbook;
import com.example.manualproject.model.User;
import com.example.manualproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class WorkbookController {
    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private HibernateSearchService searchService;

    @Autowired
    private RatingService ratingService;

    @ModelAttribute("loggedInUser")
    public User getUser(@AuthenticationPrincipal CustomUserDetails customUser) {
        if (customUser != null) return userService.findById(customUser.getId());
        else return null;
    }


    @RequestMapping(value = "/user/{userId}/add", method = RequestMethod.GET)
    public ModelAndView showAddPage() {
        List<Category> listOfCategories = categoryService.findAll();
        ModelAndView mav = new ModelAndView("add", "listOfCategories", listOfCategories);
        return mav;
    }

    @RequestMapping(value = "/user/{userId}/add", method = RequestMethod.POST)
    public @ResponseBody
    String add(@RequestBody String json, @PathVariable long userId) {
        workbookService.processWorkbook(json, userId);
        return "done";
    }

    @RequestMapping(value = "/user/action/delete", method = RequestMethod.GET)
    public @ResponseBody
    String delete(@RequestParam(value = "idArray[]") String[] idArray) {
        workbookService.delete(idArray);
        return "done";
    }

    @RequestMapping(value = "/workbook/{workbookId}", method = RequestMethod.GET)
    public ModelAndView showWorkbook(@PathVariable long workbookId, @AuthenticationPrincipal CustomUserDetails customUser) {
        ModelAndView mav = new ModelAndView("workbook", "workbook", workbookService.findById(workbookId));
        if (customUser != null)
            mav.addObject("loggedInUserRating", ratingService.getUserRating(customUser.getId(), workbookId));
        return mav;
    }

    @RequestMapping(value = "/workbook/{workbookId}/comment", method = RequestMethod.POST)
    public @ResponseBody
    String addComment(@RequestBody String json, @PathVariable long workbookId) {
        commentService.processComment(json, workbookId);
        return "done";
    }

    @RequestMapping(value = "/workbook/{workbookId}/comment/delete", method = RequestMethod.GET)
    public @ResponseBody
    String deleteComment(@RequestParam(value = "id") String id) {
        commentService.deleteComment(Long.parseLong(id));
        return "done";
    }

    @RequestMapping(value = "/search/tag/{tagName}", method = RequestMethod.GET)
    public ModelAndView search(@PathVariable String tagName) {
        List<Workbook> searchResults = null;
        searchResults = searchService.search(tagName, "tag");
        return new ModelAndView("search", "workbooks", searchResults);
    }

    @RequestMapping(value = "/workbook/{workbookId}/rating", method = RequestMethod.POST)
    public @ResponseBody
    String addRating(@RequestBody String json, @PathVariable long workbookId) {
        ratingService.addRating(json, workbookId);
        return "done";
    }
}
