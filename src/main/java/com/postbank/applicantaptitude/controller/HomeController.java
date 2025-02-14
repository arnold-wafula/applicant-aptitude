package com.postbank.applicantaptitude.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/instructions")
    public String showInstructions(HttpSession session, Model model) {
        String user = (String) session.getAttribute("AUTHENTICATED_USER");

        if (user == null) {
            return "redirect:/";  // ✅ Redirect to index if not logged in
        }

        model.addAttribute("idNumber", user);
        return "instructions";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // ✅ Destroy session on logout
        return "redirect:/";
    }
}