package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */

@Controller
public class IndexController {

    String naam = "Anouk";

    private static final Logger log =
            LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/")
    public String showIndex(Model model){
        log.debug("Startpagina opgevraagd");
        log.info("Startpagina getoond om {}", LocalTime.now());
        model.addAttribute("activePage", "home");
        model.addAttribute("naam", naam);
        return "index";
    }

}
