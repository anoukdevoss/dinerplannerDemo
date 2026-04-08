package nl.miwnn.cohort19.anouk.dinerplannerDemo.model;

import com.opencsv.bean.CsvBindByName;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"lastName", "firstName"}))
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CsvBindByName
    @NotBlank(message = "Voornaam mag niet leeg zijn")
    private String firstName;

    @CsvBindByName
    @NotBlank(message = "Achternaam mag niet leeg zijn")
    private String lastName;

    @ManyToMany(mappedBy = "guests")
    private List<Diner> diners = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    public Guest() {
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Diner> getDiners() {
        return diners;
    }

    public void setDiners(List<Diner> diners) {
        this.diners = diners;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}