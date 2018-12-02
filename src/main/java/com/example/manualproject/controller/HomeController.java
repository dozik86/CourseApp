package com.example.manualproject.controller;

import com.example.manualproject.model.Workbook;
import com.example.manualproject.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private WorkbookService workbookService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private HibernateSearchService searchService;

    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView index(@AuthenticationPrincipal CustomUserDetails customUser) {
        ModelAndView mav = new ModelAndView("index");
        if (customUser != null) mav.addObject("user", userService.findById(customUser.getId()));
        mav.addObject("lastWorkbooks", workbookService.find4LastWorkbooks());
        mav.addObject("topWorkbooks", workbookService.find4TopWorkbooks());
        return mav;
    }

    @RequestMapping(value = "/tagcloud", method = RequestMethod.GET)
    public @ResponseBody
    String sendTags() {
        return tagService.getTagsList().toString();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "search", required = false) String q) {
        List<Workbook> searchResults = null;
        if (q != null) searchResults = searchService.search(q, "global");
        return new ModelAndView("search", "workbooks", searchResults);
    }
}
