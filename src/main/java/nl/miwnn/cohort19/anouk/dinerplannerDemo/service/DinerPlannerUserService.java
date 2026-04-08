package nl.miwnn.cohort19.anouk.dinerplannerDemo.service;

import nl.miwnn.cohort19.anouk.dinerplannerDemo.dto.NewDinerPlannerUserDTO;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.DinerPlannerUser;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.repository.UserRepository;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.service.mapper.DinerPlannerUserMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Author: Anouk de Vos
 * Handle all businesslogic regarding users
 */
@Service
public class DinerPlannerUserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DinerPlannerUserService(UserRepository userRepository,
                                   PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 🔥 BELANGRIJK:
     * Dit is de koppeling met Spring Security login
     */
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "Gebruiker niet gevonden: " + username
                        )
                );
    }

    /**
     * Nieuwe gebruiker opslaan
     */
    public void saveNewUser(NewDinerPlannerUserDTO dto) {
    DinerPlannerUser user =
            DinerPlannerUserMapper.toDinerPlannerUser(dto, passwordEncoder);

        userRepository.save(user);
    }

    /**
     * Gebruiker verwijderen
     */
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Alle users ophalen
     */
    public Iterable<DinerPlannerUser> getAllUsers() {
        return userRepository.findAll();
    }
}