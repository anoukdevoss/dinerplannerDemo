package nl.miwnn.cohort19.anouk.dinerplannerDemo.repository;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.DinerPlannerUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Author: Anouk de Vos
 */
public interface UserRepository extends JpaRepository<DinerPlannerUser, Long> {
    Optional<DinerPlannerUser> findByUsername(String username);
}
