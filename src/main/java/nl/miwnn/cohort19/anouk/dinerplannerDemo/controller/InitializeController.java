package nl.miwnn.cohort19.anouk.dinerplannerDemo.controller;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.DinerPlannerUser;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Guest;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Menu;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.DinerRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.GuestRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.MenuRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.UUID;

/**
 * Author: Anouk de Vos
 */
@Component
public class InitializeController {

    private final DinerRepository dinerRepository;
    private final GuestRepository guestRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final Logger log = LoggerFactory.getLogger(InitializeController.class);

    public InitializeController(
            DinerRepository dinerRepository,
            GuestRepository guestRepository,
            MenuRepository menuRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.dinerRepository = dinerRepository;
        this.guestRepository = guestRepository;
        this.menuRepository = menuRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void seed() {
        if (guestRepository.count() == 0) {
            seedGuests();
        }
        if (dinerRepository.count() == 0) {
            seedDiners();
        }
        if (userRepository.count() == 0){
            DinerPlannerUser admin = new DinerPlannerUser(
                    "beheerder",
                    passwordEncoder.encode("welkom123"),
                    true);
            userRepository.save(admin);
        }
    }

    private void seedGuests() {
        try {
            for (Diner diner : dinerRepository.findAll()) {
                diner.getGuests().clear();
                dinerRepository.save(diner);
            }

            guestRepository.deleteAll();

            ClassPathResource resource =
                    new ClassPathResource("guests.csv");
            Reader reader = new InputStreamReader(
                    resource.getInputStream());

            CsvToBean<Guest> csvToBean =
                    new CsvToBeanBuilder<Guest>(reader)
                            .withType(Guest.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

            List<Guest> guests = csvToBean.parse();

            for (Guest guest : guests) {
                guestRepository.save(guest);
            }

        } catch (IOException e) {
            throw new RuntimeException(
                    "Kon gasten.csv niet inlezen", e);
        }
        }

    private void seedDiners() {
        try {
            ClassPathResource resource = new ClassPathResource("diners.csv");
            Reader reader = new InputStreamReader(resource.getInputStream());

            CsvToBean<Diner> csvToBean =
                    new CsvToBeanBuilder<Diner>(reader)
                            .withType(Diner.class)
                            .withIgnoreLeadingWhiteSpace(true)
                            .build();

            List<Diner> diners = csvToBean.parse();
            List<Guest> guests = guestRepository.findAll();

            for (int i = 0; i < diners.size(); i++) {
                Diner diner = diners.get(i);
                diner.getGuests().add(guests.get(i % guests.size()));
                dinerRepository.save(diner);

                menuRepository.save(new Menu(diner));
                menuRepository.save(new Menu(diner));
            }
        } catch (IOException e) {
            throw new RuntimeException("Kon diners.csv niet inlezen", e);
        }
    }
}