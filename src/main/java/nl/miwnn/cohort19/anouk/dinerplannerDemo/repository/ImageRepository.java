package nl.miwnn.cohort19.anouk.dinerplannerDemo.repository;

import jakarta.validation.Valid;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Diner;
import nl.miwnn.cohort19.anouk.dinerplannerDemo.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

/**
 * Author: Anouk de Vos
 * !! Doel voor de class !!
 */
public interface ImageRepository extends JpaRepository<Image, Long> {

}
