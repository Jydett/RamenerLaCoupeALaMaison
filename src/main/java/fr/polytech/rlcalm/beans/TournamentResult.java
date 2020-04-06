package fr.polytech.rlcalm.beans;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class TournamentResult {
    @Id
    private Long id;

    @Setter
    private Integer score;

    @Setter
    private Tournament tournament;

    @Setter
    private Club champions;
}
