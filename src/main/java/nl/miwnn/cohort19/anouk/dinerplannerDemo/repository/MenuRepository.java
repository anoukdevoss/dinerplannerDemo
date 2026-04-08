package nl.miwnn.cohort19.anouk.dinerplannerDemo.repository;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
