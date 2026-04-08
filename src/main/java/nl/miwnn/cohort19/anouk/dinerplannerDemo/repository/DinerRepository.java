package nl.miwnn.cohort19.anouk.dinerplannerDemo.repository;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */
public interface DinerRepository extends JpaRepository<Diner, Long> {

    Optional<Diner> findByTitle(String title);

    List<Diner> findByTitleContainingIgnoreCase(String keyword);

    @Query("SELECT d FROM Diner d JOIN d.guests a " +
            "WHERE LOWER(a.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Diner> findByGuestLastNameContaining(
            @Param("name") String name);
}
