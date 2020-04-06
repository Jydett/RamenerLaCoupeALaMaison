package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class Participation {
    @Id
    private Long id;

    @Setter
    private Player player;

    @Setter
    private Integer goals;

    @Setter
    private Match match;
}
