package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Menu;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.DinerRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.MenuRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Author: Anouk de Vos
 * Controller voor het beheren van menu's binnen een diner.
 */

@Controller
@RequestMapping("/menus")
public class MenuController {

    private final MenuService menuService;
    private final DinerRepository dinerRepository;
    private final MenuRepository menuRepository;


    public MenuController(MenuService menuService, DinerRepository dinerRepository, MenuRepository menuRepository) {
        this.menuService = menuService;
        this.dinerRepository = dinerRepository;
        this.menuRepository = menuRepository;
    }

    @PostMapping("/publish/{menuId}")
    public String publishMenu(@PathVariable Long menuId, RedirectAttributes redirectAttributes){
        try {
            menuService.publishMenu(menuId);
            redirectAttributes.addFlashAttribute(
                    "succesMessage",
                    "Menu succesvol gepubliceerd");
        } catch (IllegalStateException illegalStateException) {
            redirectAttributes.addFlashAttribute("errorMessage", illegalStateException.getMessage());
        }
        return "redirect:/diners";
    }

    @PostMapping("/unpublish/{menuId}")
    public String unpublishMenu  (@PathVariable Long menuId, RedirectAttributes redirectAttributes){
        try {
            menuService.unpublishMenu(menuId);
            redirectAttributes.addFlashAttribute(
                    "succesMessage",
                    "Menu succesvol ongepubliceerd");
        } catch (IllegalStateException illegalStateException) {
            redirectAttributes.addFlashAttribute("errorMessage", illegalStateException.getMessage());
        }
        return "redirect:/diners";
    }

    @PostMapping("/add/{dinerId}")
    public String addMenu(@PathVariable Long dinerId) {
        Diner diner = dinerRepository.findById(dinerId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Diner niet gevonden"));

        Menu menu = new Menu();
        menu.setDiner(diner);
        menu.setPublished(false);

        menuRepository.save(menu);

        return "redirect:/diners/" + dinerId;
    }

    @PostMapping("/delete/{menuId}")
    public String deleteMenu(@PathVariable Long menuId) {

        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Menu niet gevonden"));

        Long dinerId = menu.getDiner().getId();

        menuRepository.delete(menu);

        return "redirect:/diners/" + dinerId;
    }

}
