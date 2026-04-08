package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Image;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.DinerRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.GuestRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.ImageRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.MenuRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diners")
public class DinerController {

    private static final Logger log = LoggerFactory.getLogger(DinerController.class);
    private final DinerRepository dinerRepository;
    private final GuestRepository guestRepository;
    private final ImageRepository imageRepository;

    public DinerController(DinerRepository dinerRepository, GuestRepository guestRepository, ImageRepository imageRepository) {
        this.dinerRepository = dinerRepository;
        this.guestRepository = guestRepository;
        this.imageRepository = imageRepository;
    }

    @Value("${diner.max-menus-per-diner:3}")
    private int maxMenusPerDiner;

    @GetMapping("")
    public String showDiners(
            @RequestParam(required = false) String query,
            Model model) {

        List<Diner> displayDiners;

        if (query != null && !query.isBlank()) {
            log.debug("Zoeken op query: {}", query);
            displayDiners = dinerRepository.findByTitleContainingIgnoreCase(query);
        } else {
            displayDiners = dinerRepository.findAll();
        }

        model.addAttribute("diners", displayDiners);
        model.addAttribute("query", query);
        model.addAttribute("activePage", "diners");
        return "diner-overview";
    }

    @GetMapping("/save")
    public String showAddForm(Model model) {
        model.addAttribute("diner", new Diner());
        model.addAttribute("allGuests", guestRepository.findAll());
        return "add-edit-diner";
    }

    @PostMapping("/save")
    public String saveDiner(
            @Valid @ModelAttribute Diner updatedDiner,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes redirectAttributes,
            @RequestParam("imageFile") MultipartFile imageFile) {

        log.info("Diner opslaan: {}", updatedDiner.getTitle());

        if (bindingResult.hasErrors()) {
            log.warn("Validatiefouten bij opslaan: {}",
                    bindingResult.getErrorCount());
            model.addAttribute("allGuests", guestRepository.findAll());
            return "add-edit-diner";
        }

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                Image image = new Image();
                image.setContentType(imageFile.getContentType());
                image.setData(imageFile.getBytes());

                Image savedImage = imageRepository.save(image);
                updatedDiner.setImage(savedImage);
            }

            dinerRepository.save(updatedDiner);

        } catch (IOException e) {
            throw new RuntimeException("Afbeelding opslaan mislukt", e);
        }

        log.info("Diner opgeslagen: {}", updatedDiner.getTitle());
        redirectAttributes.addFlashAttribute(
                "successMessage", "Diner succesvol opgeslagen");

        String encodedTitle =
                UriUtils.encodePath(updatedDiner.getTitle(), StandardCharsets.UTF_8);
        return "redirect:/diners";
    }

    @PostMapping("/delete/{dinerId}")
    public String deleteDiner(@PathVariable Long dinerId) {
        log.info("Verwijderverzoek ontvangen voor dinerId: {}", dinerId);
        dinerRepository.deleteById(dinerId);
        return "redirect:/diners";
    }

    @GetMapping("/edit/{dinerId}")
    public String showEditForm(@PathVariable Long dinerId, Model model) {
        log.info("Bewerkformulier geopend voor: {}", dinerId);
        Optional<Diner> diner = dinerRepository.findById(dinerId);

        if (diner.isEmpty()) {
            log.warn("Diner niet gevonden met id: {}", dinerId);
            return "redirect:/diners";
        }

        model.addAttribute("diner", diner.get());
        model.addAttribute("allGuests", guestRepository.findAll());
        return "add-edit-diner";
    }

    @GetMapping("/{dinerId}")
    public String showDinerDetail(
            @PathVariable Long dinerId,
            Model model) {

        Diner diner = dinerRepository.findById(dinerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Diner niet gevonden"));

        model.addAttribute("diner", diner);
        return "diner-detail";
    }

    // test


}