package com.example.registrationservice.controller;

import com.example.registrationservice.model.dto.RegistrationDto;
import com.example.registrationservice.model.dto.UserDto;
import com.example.registrationservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        RegistrationDto userDto = new RegistrationDto();

        model.addAttribute("userDto", userDto);

    return "registration/form";
    }

    @PostMapping("/registration")
    public String processRegistrationForm(@ModelAttribute("userDto") RegistrationDto userDto,
                                          BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration/form";
        }

        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userService.registerNewUser(userDto);

        // Перенаправляем пользователя на страницу с подтверждением
        redirectAttributes.addFlashAttribute("message", "Вы успешно зарегистрировались!");
        return "redirect:/registration/success";
    }

    @GetMapping("/registration/success")
    public String showSuccessPage() {
        return "registration/success";
    }

}

