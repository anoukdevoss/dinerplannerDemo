package nl.miwnn.cohort19.anouk.dinerplannerDemo.service.mapper;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.dto.NewDinerPlannerUserDTO;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.DinerPlannerUser;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author: Anouk de Vos
 */
public class DinerPlannerUserMapper {

    public static DinerPlannerUser toDinerPlannerUser(
            NewDinerPlannerUserDTO dto,
            PasswordEncoder passwordEncoder) {

        DinerPlannerUser user = new DinerPlannerUser();

        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPlainPassword()));
        user.setAdministrator(dto.isAdministrator());

        return user;
    }
}