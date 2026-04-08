package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.dto.NewDinerPlannerUserDTO;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.service.DinerPlannerUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Author: Anouk de Vos
 * Handle all HTTP requests regarding DinerPlannerUsers
 */
@Controller
@RequestMapping("/users")
public class DinerPlannerUserController {

    private final DinerPlannerUserService dinerPlannerUserService;
    private final PasswordEncoder passwordEncoder;

    public DinerPlannerUserController(
            DinerPlannerUserService dinerPlannerUserService,
            PasswordEncoder passwordEncoder) {
        this.dinerPlannerUserService = dinerPlannerUserService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/all")
    public String showUserOverview(Model model) {
        model.addAttribute("users", dinerPlannerUserService.getAllUsers());
        return "user-overview";
    }

    @GetMapping("/add")
    public String showAddUserForm(Model model) {
        model.addAttribute("newUser", new NewDinerPlannerUserDTO());
        return "user-form";
    }

    @PostMapping("/add")
    public String addUser(
            @Valid @ModelAttribute("newUser") NewDinerPlannerUserDTO dto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (!dto.getPlainPassword().equals(dto.getCheckPassword())) {
            bindingResult.rejectValue(
                    "plainPassword",
                    "unequal",
                    "Wachtwoorden moeten gelijk zijn."
            );
        }

        if (bindingResult.hasErrors()) {
            return "user-form";
        }

        dinerPlannerUserService.saveNewUser(dto);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Gebruiker '" + dto.getUsername() + "' is aangemaakt."
        );

        return "redirect:/users/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        dinerPlannerUserService.deleteById(id);

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Gebruiker verwijderd."
        );

        return "redirect:/users/all";
    }
}