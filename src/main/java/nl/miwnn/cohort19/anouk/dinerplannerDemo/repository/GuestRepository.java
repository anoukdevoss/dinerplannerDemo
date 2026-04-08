package nl.miwnn.cohort19.anouk.dinerplannerDemo.repository;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest>findByLastNameAndFirstName(
            String lastName, String firstName);
}
