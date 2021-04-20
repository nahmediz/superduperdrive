package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {

    private UserServices userServices;

    public SignupController(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping
    public String signupView() {
        return "signup";
    }

    @PostMapping
    public String signupUser(@ModelAttribute User user, Model model) {
        String signupError = "";

        if (!userServices.isUsernameAvailable(user.getUsername())) {
            signupError = "The username already exists.";
        }

        if (signupError.isEmpty()) {
            int addedRows = userServices.createUser(user);
            if (addedRows < 0) {
                signupError = "There was an error signing you up. Please try again later.";
            }
        }

        if (signupError.isEmpty()) {
            model.addAttribute("signupSuccess", true);
        } else {
            model.addAttribute("signupError", signupError);
        }
        return "signup";
    }
}
