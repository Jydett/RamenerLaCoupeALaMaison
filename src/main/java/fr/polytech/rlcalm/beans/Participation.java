package fr.polytech.rlcalm.beans;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Participation {
    @Id
    private Long id;

    private Player player;

    private Integer goals;

    private Match match;
}
