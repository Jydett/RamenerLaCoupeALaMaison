package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Participation {
    @Id
    private Long id;

    @ManyToOne
    private Player player;

    private Integer goals;

    @ManyToOne
    private Match match;
}
