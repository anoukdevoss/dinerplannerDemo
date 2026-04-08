package nl.miwnn.cohort19.anouk.dinerplannerDemo.model;

import jakarta.persistence.*;

/**
 * Author: Anouk de Vos
 * Entity die een menu representeert dat hoort bij een diner.
 */

@Entity
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diner_id")
    private Diner diner;

    private boolean published;

    public Menu(Diner diner) {
        this.diner = diner;
        this.published = false;
    }

    public Menu() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Diner getDiner() {
        return diner;
    }

    public void setDiner(Diner diner) {
        this.diner = diner;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }


}
