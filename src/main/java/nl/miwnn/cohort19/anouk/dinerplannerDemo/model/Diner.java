package nl.miwnn.cohort19.anouk.dinerplannerDemo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Anouk de Vos
 * Entity die een diner representeert met bijbehorende menu's en gasten.
 */

@Entity
public class Diner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "diner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @Column(unique = true)
    @NotBlank(message = "Titel mag niet leeg zijn")
    @Size(max = 200, message = "Titel mag maximaal 200 tekens bevatten")
    private String title;

    @NotBlank(message = "Datum mag niet leeg zijn")
    private String date;

    @NotBlank(message = "Locatie mag niet leeg zijn")
    private String location;

    @NotBlank(message = "Thema mag niet leeg zijn")
    private String thema;

    private String imagePath;

    @Column(nullable = true, length = 2000)
    private String description;

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "diner_guest",
            joinColumns = @JoinColumn(name = "diner_id"),
            inverseJoinColumns = @JoinColumn(name = "guest_id")
    )
    private List<Guest> guests = new ArrayList<>();


    public Diner(String title, String date, String location, String thema, String imagePath) {
        this.title = title;
        this.date = date;
        this.location = location;
        this.thema = thema;
        this.imagePath = imagePath;
    }

    public Diner() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getThema() {
        return thema;
    }

    public void setThema(String thema) {
        this.thema = thema;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Menu> getMenus() {
        return menus;
    }

    public void setMenus(List<Menu> menus) {
        this.menus = menus;
    }

    public List<Guest> getGuests() {
        return guests;
    }

    public void setGuests(List<Guest> guests) {
        this.guests = guests;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
