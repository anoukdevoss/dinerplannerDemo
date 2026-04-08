package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Guest;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Image;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.GuestRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Optional;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */
@Controller
@RequestMapping("/guests")
public class GuestController {

    private final GuestRepository guestRepository;
    private final ImageRepository imageRepository;

    public GuestController(GuestRepository guestRepository,
                           ImageRepository imageRepository) {
        this.guestRepository = guestRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("")
    public String showOverview (Model model) {
        model.addAttribute("activePage", "guests");
        model.addAttribute("allGuests", guestRepository.findAll());
        model.addAttribute("newGuest", new Guest());
        return "guest-overview";
    }

    @GetMapping("/{id}")
    public String showGuestDetail(@PathVariable Long id, Model model) {

        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Gast niet gevonden"));

        model.addAttribute("guest", guest);
        return "guest-detail";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Guest> guest = guestRepository.findById(id);
        if (guest.isEmpty()) {
            return "redirect:/guests";
        }
        model.addAttribute("newGuest", guest.get());
        model.addAttribute("allGuests", guestRepository.findAll());
        return "guest-overview";
    }
    @PostMapping("/delete/{id}")
    public String deleteGuest(@PathVariable Long id) {
        if (!guestRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Gast niet gevonden");
        }

        guestRepository.deleteById(id);
        return "redirect:/guests";
    }

    @PostMapping("/save")
    public String saveGuest(
            @Valid @ModelAttribute("newGuest") Guest guest,
            BindingResult bindingResult,
            Model model,
            @RequestParam("imageFile") MultipartFile imageFile) throws IOException {

    if (bindingResult.hasErrors()) {
        model.addAttribute("allGuests", guestRepository.findAll());
        model.addAttribute("newGuest", guest);
        return "guest-overview";
    }

        if (!imageFile.isEmpty()) {
            Image image = new Image();
            image.setData(imageFile.getBytes());
            image.setContentType(imageFile.getContentType());
            imageRepository.save(image);
            guest.setImage(image);
        }

    guestRepository.save(guest);
        return "redirect:/guests/" + guest.getId();
    }

}
