package com.postbank.applicantaptitude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/instructions")
    public String showInstructions(@RequestParam String id, Model model) {
        model.addAttribute("idNumber", id); // Pass ID number to view
        return "instructions";
    }
}