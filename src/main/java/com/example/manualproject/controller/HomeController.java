package com.example.manualproject.controller;

import com.example.manualproject.model.Category;
import com.example.manualproject.model.Instruction;
import com.example.manualproject.model.User;
import com.example.manualproject.repository.CategoryRepository;
import com.example.manualproject.service.*;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private InstructionService instructionService;

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
        mav.addObject("lastInstructions", instructionService.find4LastInstructions());
        mav.addObject("topInstructions", instructionService.find4TopInstructions());
        return mav;
    }

    @RequestMapping(value = "/tagcloud", method = RequestMethod.GET)
    public @ResponseBody
    String sendTags() {
        return tagService.getTagsList().toString();
    }

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "search", required = false) String q) {
        List<Instruction> searchResults = null;
        if (q != null) searchResults = searchService.search(q, "global");
        return new ModelAndView("search", "instructions", searchResults);
    }
}
